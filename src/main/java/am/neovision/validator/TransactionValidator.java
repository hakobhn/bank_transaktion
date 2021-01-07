package am.neovision.validator;

import am.neovision.dto.TransactionAdd;
import am.neovision.service.BankAccountService;
import am.neovision.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class TransactionValidator implements Validator {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private TransactionService transactionService;

    @Override
    public boolean supports(Class clazz) {
        //just validate the Customer instances
        return TransactionAdd.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TransactionAdd transactionAdd = (TransactionAdd) target;

        bankAccountService.findByNumber(transactionAdd.getFromNumber())
                .orElseGet(() -> {
                    errors.rejectValue("fromNumber", "bank.number.not.found", "Provided number is incorrect");
                    return null;
                });

        bankAccountService.findByNumber(transactionAdd.getToNumber().replaceAll("[^0-9\\s.]", ""))
                .orElseGet(() -> {
                    errors.rejectValue("toNumber", "bank.number.not.found", "Provided number is incorrect");
                    return null;
                });

        if (transactionAdd.getFromNumber().equals(transactionAdd.getToNumber())) {
            errors.rejectValue("toNumber", "bank.number.same", "Impossible to transfer to same account");
        }

        try {
            Float amount = Float.parseFloat(transactionAdd.getAmount().replaceAll("[^a-zA-Z0-9\\s.]", ""));
            if (amount < 0f) {
                errors.rejectValue("amount", "amount.empty", "Amount not set");
            }
        } catch (Exception e) {
            errors.rejectValue("amount", "amount.incorrect", "Amount not correct");
        }
    }
}
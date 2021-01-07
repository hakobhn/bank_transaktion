package am.neovision.validator;

import am.neovision.dto.AccountAdd;
import am.neovision.dto.BankAccountAdd;
import am.neovision.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class BankAccountValidator implements Validator {

    @Autowired
    private BankAccountService bankAccountService;

    @Override
    public boolean supports(Class clazz) {
        //just validate the Customer instances
        return AccountAdd.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BankAccountAdd bankAccountAdd = (BankAccountAdd) target;

        bankAccountService.findByNumber(bankAccountAdd.getNumber().replaceAll("[^0-9\\s.]", ""))
                .ifPresent(bacc ->
                        errors.rejectValue("number", "number.in.us", "Provided number is already being used")
                );

        try {
            Float amount = Float.parseFloat(bankAccountAdd.getAmount().replaceAll("[^a-zA-Z0-9\\s.]", ""));
            if (amount < 0f) {
                errors.rejectValue("amount", "amount.empty", "Amount not set");
            }
        } catch (Exception e) {
            errors.rejectValue("amount", "amount.incorrect", "Amount not correct");
        }
    }
}

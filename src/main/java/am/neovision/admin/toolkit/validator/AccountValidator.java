package am.neovision.admin.toolkit.validator;

import am.neovision.admin.toolkit.dto.AccountAdd;
import am.neovision.admin.toolkit.service.impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Optional;

@Service
public class AccountValidator implements Validator {

    @Value("${root.email}")
    private String rootEmail;

    @Autowired
    private AccountService accountService;

    @Override
    public boolean supports(Class clazz) {
        //just validate the Customer instances
        return AccountAdd.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
                "required.password", "Field is required.");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rePassword",
                "required.rePassword", "Field is required.");

        AccountAdd account = (AccountAdd) target;

        if (!(account.getPassword().equals(account.getRePassword()))) {
            errors.rejectValue("password", "notmatch.password", "Passwords do not match");
        }

        if (account.getEmail().equalsIgnoreCase(rootEmail)) {
            errors.rejectValue("email", "email.is.restricted", "Provided email address is restricted.");
        }
        Optional.ofNullable(accountService.findByEmail(account.getEmail()))
                .ifPresent(usr -> {
                            if (!usr.getUuid().equals(account.getUuid())) {
                                errors.rejectValue("email", "email.in.us", "Provided email address is already being used");
                            }
                        }
                );
    }
}

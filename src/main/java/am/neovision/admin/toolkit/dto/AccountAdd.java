package am.neovision.admin.toolkit.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author hakob.hakobyan created on 11/30/2020
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountAdd extends AbstractDto {
    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;
    @NotBlank(message = "First name is required")
    @Pattern(regexp = "^[\\p{Alnum}]{2,32}$", message = "First name is invalid")
    private String firstName;
    @NotBlank(message = "Last name is required")
    @Pattern(regexp = "^[\\p{Alnum}]{2,32}$", message = "Last name is invalid")
    private String lastName;
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^[\\p{Alnum}]{5,32}$", message = "Password is too short")
    private String password;
    @NotBlank(message = "Confirm password is required")
    private String rePassword;

    @URL
    private String avatar;

    private Gender gender = Gender.MALE;
    private Currency currency;
}

package am.neovision.admin.toolkit.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AccountDto extends AbstractDto {
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;
    private GenderEnum gender;
}

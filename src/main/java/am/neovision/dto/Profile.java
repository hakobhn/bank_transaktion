package am.neovision.dto;

import lombok.Data;

@Data
public class Profile {
    private String fullName;
    private String avatar;
    private String email;
    private Currency currency;
}

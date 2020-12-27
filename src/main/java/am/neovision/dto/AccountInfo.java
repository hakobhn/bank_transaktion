package am.neovision.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountInfo {
    private String uuid;
    private String fullName;
    private String avatar;
}

package am.neovision.admin.toolkit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountInfo {
    private String uuid;
    private String fullName;
    private String avatar;
}

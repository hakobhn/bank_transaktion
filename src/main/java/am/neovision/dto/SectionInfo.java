package am.neovision.dto;

import lombok.Data;

@Data
public class SectionInfo {
    private String title;
    private String description;
    private boolean isAdmin;

    public boolean isAdmin() {
        return isAdmin;
    }
}

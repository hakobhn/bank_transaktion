package am.neovision.admin.toolkit.dto;

import lombok.Data;

import java.util.Date;

@Data
public abstract class AbstractDto {
    protected String uuid;
    protected Date createdDate;
}

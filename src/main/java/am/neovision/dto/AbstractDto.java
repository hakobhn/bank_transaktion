package am.neovision.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public abstract class AbstractDto {
    protected String uuid;
}

package am.neovision.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Processing {
    @NotBlank
    private String uuid;
    @NotBlank
    private String status;
}

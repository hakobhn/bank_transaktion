package am.neovision.dto.transaction;

import am.neovision.dto.AbstractDto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class TransactionAdd extends AbstractDto {
    @NotBlank(message = "Bank number is required")
    @Pattern(regexp = "^\\d{16}$", message = "Bank number is invalid")
    private String fromNumber;
    @NotBlank(message = "Bank number is required")
    @Pattern(regexp = "^\\d{4}\\-\\d{4}\\-\\d{4}\\-\\d{4}$", message = "Bank number is invalid")
    private String toNumber;

    @NotNull(message = "Amount is required")
    private String amount;

    @NotNull(message = "Type is required")
    private TransactionType type;
}

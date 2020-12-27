package am.neovision.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class BankAccountAdd extends AbstractDto {
    @NotBlank(message = "Bank number is required")
    @Pattern(regexp = "^\\d{4}\\-\\d{4}\\-\\d{4}\\-\\d{4}$", message = "Bank number is invalid")
    private String number;

    @NotNull(message = "Amount is required")
    private Float amount;

    @NotNull(message = "Currency is required")
    private Currency currency;
}

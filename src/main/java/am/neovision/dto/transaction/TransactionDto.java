package am.neovision.dto.transaction;

import am.neovision.dto.AbstractDto;
import am.neovision.dto.BankAccountDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class TransactionDto extends AbstractDto {
    @NotBlank
    private BankAccountDto from;
    @NotBlank
    private BankAccountDto to;
    @NotBlank
    private Float fromAmount;
    @NotBlank
    private Float toAmount;
    @NotNull(message = "Status is required")
    private TransactionStatus status;
    @NotNull(message = "Type is required")
    private TransactionType type;
}

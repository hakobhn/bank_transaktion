package am.neovision.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BankAccountDto extends AbstractDto {
    private String number;
    private Float amount;
    private Float availableAmount;
    private Currency currency;
}

package am.neovision.admin.toolkit.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BankAccountDto extends AbstractDto {
    private String number;
    private Float amount;
    private Currency currency;
}

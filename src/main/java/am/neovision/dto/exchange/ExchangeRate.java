package am.neovision.dto.exchange;

import am.neovision.dto.Currency;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class ExchangeRate {
    private Map<Currency, Float> rates;
    private Currency base;
    private Date date;
}

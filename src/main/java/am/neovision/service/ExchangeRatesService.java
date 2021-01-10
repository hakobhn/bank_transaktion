package am.neovision.service;

import am.neovision.dto.Currency;

/**
 * @author hakob.hakobyan created on 12/28/2020
 */
public interface ExchangeRatesService {

    Float getExchangeRate(Currency fromCurrency, Currency toCurrency);

    Float calculateExchangeAmount(Float amount, Currency fromCurrency, Currency toCurrency);
}

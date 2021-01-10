package am.neovision.service.impl;

import am.neovision.dto.Currency;
import am.neovision.dto.exchange.ExchangeRate;
import am.neovision.exceptions.RemoteServiceException;
import am.neovision.service.ExchangeRatesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author hakob.hakobyan created on 12/28/2020
 */
@Slf4j
@Service("notificationServiceImpl")
public class ExchangeRatesServiceImpl implements ExchangeRatesService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Remote service associated with a URL to the remote api.
     *
     * @param url Points to the Foreign exchange rates API with currency conversion
     */
    @Value("${exchange-service.url}")
    private String url;

    /**
     * Retrieve exchange rate for given currency.
     *
     * @param fromCurrency The base currency object to send... we'll flatten it to request param.
     * @param toCurrency   The symbols currency object to send... we'll flatten it to request param.
     * @return Currency rate value for given currency vs result currency.
     */
    public Float getExchangeRate(Currency fromCurrency, Currency toCurrency) {
        try {
            if (fromCurrency.equals(toCurrency)) {
                return 1f;
            }
            ResponseEntity<ExchangeRate> response = restTemplate.getForEntity(
                    String.format(url, fromCurrency.getDisplayValue(), toCurrency.getDisplayValue()),
                    ExchangeRate.class);
            return response.getBody().getRates().get(toCurrency);
        } catch (Exception e) {
            log.error("Exchange service is down. Error: {}.", e.getLocalizedMessage());
            throw new RemoteServiceException("Unable retrieve data from exchange service.", e);
        }
    }

    /**
     * Calculates amount for given currency and amount.
     *
     * @param amount       The amount that needs to be converted.
     * @param fromCurrency The currency of amount.
     * @param toCurrency   The currency in which needs to be converted the amount.
     * @return Converted amount.
     */
    public Float calculateExchangeAmount(Float amount, Currency fromCurrency, Currency toCurrency) {
        return amount * getExchangeRate(fromCurrency, toCurrency);
    }
}

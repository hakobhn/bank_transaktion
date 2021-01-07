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
     * Retrieve exchage rate for given currency.
     *
     * @param currency The object to send... we'll flatten it to request param.
     *
     * @return Currency rate value for given currency vs USD.
     */
    public Float getCurrencyRate(Currency currency) {
        try {
            ResponseEntity<ExchangeRate> response = restTemplate.postForEntity(url, currency.getDisplayValue(), ExchangeRate.class);
            return response.getBody().getRates().get(currency);
        } catch (Exception e) {
            log.error("Notification service is down. Error: {}.", e.getLocalizedMessage());
            throw new RemoteServiceException("Unable retrieve data from exchange service.", e);
        }
    }
}

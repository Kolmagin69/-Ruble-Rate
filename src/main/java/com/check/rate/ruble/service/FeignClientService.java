package com.check.rate.ruble.service;

import com.check.rate.ruble.client.api.giphy.com.ApiGiphyComClient;
import com.check.rate.ruble.client.api.giphy.com.item.ApiGiphyComResponse;
import com.check.rate.ruble.client.open.change.rates.OpenExchangeRatesClient;
import com.check.rate.ruble.client.open.change.rates.item.ExchangeRate;
import com.check.rate.ruble.client.open.change.rates.item.OpenChangeRatesResponse;
import com.check.rate.ruble.exception.RateNotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

@Service
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
public class FeignClientService {

    @Autowired
    private OpenExchangeRatesClient openExchangeRatesClient;

    @Autowired
    private ApiGiphyComClient apiGiphyComClient;

    @Value("${open.change.rates.app_id}") String appId;

    @Value("${api.giphy.com.api_key}") String apiKey;

    @Value("${api.giphy.com.tag_rich}") String tagRich;

    @Value("${api.giphy.com.tag_broke}") String tagBroke;

    @Value("${api.giphy.com.rating}") String rating;

    public String checkRubleRateAndGetGif(final ExchangeRate compareWith, final String historicalDate)
            throws IllegalArgumentException{
        if(compareWith == null || historicalDate == null)
            throw new IllegalArgumentException("You must declare ExchangeRate:compareWith, String:historicalDate");
        if(!isValidStringDate(historicalDate))
            throw new IllegalArgumentException("Invalid date. Format YYYY-MM-DD and date must be before " +
                    LocalDate.now().toString());

        final OpenChangeRatesResponse openChangeRatesResponse = openExchangeRatesClient.getLatestRates(appId);
        final Map<ExchangeRate, Double> latestRate = openChangeRatesResponse.getActualRates();

        final Map<ExchangeRate, Double> historicalRate = openExchangeRatesClient
                .getHistoricalRates(historicalDate,appId)
                .getActualRates();

        if(isRubleRateIncreased(latestRate, historicalRate, compareWith))
            return getUrlFromResponse(apiGiphyComClient.getRandomGiphy(apiKey,tagRich,rating));
        return getUrlFromResponse(apiGiphyComClient.getRandomGiphy(apiKey,tagBroke,rating));
    }

    private boolean isValidStringDate(final String historicalDate) {
        LocalDate localDateHistorical;
        try {
            localDateHistorical = LocalDate.parse(historicalDate);
        } catch (DateTimeParseException e) {
            return false;
        }
        LocalDate currentLocalDate = LocalDate.now();
        if(localDateHistorical.isBefore(LocalDate.of(1999, 2, 1)))
            return false;
        return !currentLocalDate.isBefore(localDateHistorical);
    }

    private boolean isRubleRateIncreased(final Map<ExchangeRate, Double> latestRate,
                                       final Map<ExchangeRate, Double> historicalRate,
                                       final ExchangeRate compareWith) {
        final double rubleLatestRate = latestRate.get(ExchangeRate.RUB);
        final double rubleHistoricalRate = historicalRate.get(ExchangeRate.RUB);
        if (compareWith == ExchangeRate.USD) {
            return rubleHistoricalRate > rubleLatestRate;
        }
        double comparedLatestRate, comparedHistoricalRate;
        try {
            comparedLatestRate = latestRate.get(compareWith);
            comparedHistoricalRate = historicalRate.get(compareWith);
        } catch (NullPointerException e) {
            throw new RateNotSupportedException("Currency exchange rate not supported / wasn`t supported ");
        }



        final double rubleRateRelationComparedRateHistorical = rubleHistoricalRate / comparedHistoricalRate;
        final double rubleRateRelationComparedRateLatest = rubleLatestRate / comparedLatestRate;
        return rubleRateRelationComparedRateHistorical < rubleRateRelationComparedRateLatest;
    }

    private String getUrlFromResponse(final ApiGiphyComResponse apiGiphyComResponse) {
        return apiGiphyComResponse.getData().getImages().getFixed_height().getUrl();
    }

}

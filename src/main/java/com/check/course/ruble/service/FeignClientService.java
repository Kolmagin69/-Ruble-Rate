package com.check.course.ruble.service;

import com.check.course.ruble.client.api.giphy.com.ApiGiphyComClient;
import com.check.course.ruble.client.api.giphy.com.item.ApiGiphyComResponse;
import com.check.course.ruble.client.open.change.rates.OpenExchangeRatesClient;
import com.check.course.ruble.client.open.change.rates.item.ExchangeRate;
import com.check.course.ruble.client.open.change.rates.item.OpenChangeRatesResponse;
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

    public String checkRubleRateAndGetGif(final ExchangeRate compareWith, final String historicalDate) {
        if(compareWith == null || historicalDate == null)
            throw new IllegalArgumentException("You must declare ExchangeRate:compareWith, String:historicalDate");
        if(!isValidStringDate(historicalDate))
            throw new IllegalArgumentException("Invalid date");

        final OpenChangeRatesResponse openChangeRatesResponse = openExchangeRatesClient.getLatestRates(appId);
        final Map<ExchangeRate, Double> latestRate = openChangeRatesResponse.getRates();

        final Map<ExchangeRate, Double> historicalRate = openExchangeRatesClient
                .getHistoricalRates(historicalDate,appId)
                .getRates();

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
        final double comparedLatestRate = latestRate.get(compareWith);
        final double comparedHistoricalRate = historicalRate.get(compareWith);

        final double changesRubleRate = rubleHistoricalRate / rubleLatestRate;
        final double changesComparedRate = comparedHistoricalRate / comparedLatestRate;
        return changesRubleRate > changesComparedRate;
    }

    private String getUrlFromResponse(final ApiGiphyComResponse apiGiphyComResponse) {
        return apiGiphyComResponse.getData().getImages().getFixed_height().getUrl();
    }

}

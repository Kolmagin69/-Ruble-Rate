package com.check.rate.ruble.client.open.change.rates;

import com.check.rate.ruble.client.open.change.rates.item.OpenChangeRatesResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class OpenExchangeRatesClientTest {

    @MockBean
    private OpenExchangeRatesClient openExchangeRatesClient;

    @Test
    void testLatestRates() {
        Mockito.when(openExchangeRatesClient.getLatestRates(any()))
                .thenReturn(new OpenChangeRatesResponse());
    }

    @Test
    void testHistoricalRate() {
        Mockito.when(openExchangeRatesClient.getHistoricalRates(any(),any()))
                .thenReturn(new OpenChangeRatesResponse());
    }

}
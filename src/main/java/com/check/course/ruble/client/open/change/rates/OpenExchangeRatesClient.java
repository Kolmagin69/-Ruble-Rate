package com.check.course.ruble.client.open.change.rates;

import com.check.course.ruble.client.open.change.rates.item.OpenChangeRatesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "openexchangerates",
        url = "https://openexchangerates.org/api/")
public interface OpenExchangeRatesClient {

    @RequestMapping(method = RequestMethod.GET, value = "/latest.json")
    OpenChangeRatesResponse getLatestRates (@RequestParam("app_id") String appId);

    @RequestMapping(method = RequestMethod.GET, value = "/historical/{date}.json")
    OpenChangeRatesResponse getHistoricalRates (@PathVariable("date") String YYYY_MM_DD,
                                                @RequestParam("app_id") String appId);
}

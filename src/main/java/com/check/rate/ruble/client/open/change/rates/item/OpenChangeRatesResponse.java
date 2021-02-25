package com.check.rate.ruble.client.open.change.rates.item;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenChangeRatesResponse {

    private long timestamp;

    private String base;

    private Map<ExchangeRate, Double> rates;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Map<ExchangeRate, Double> getRates() {
        return rates;
    }

    public void setRates(HashMap<ExchangeRate, Double> rates) {
        this.rates = rates;
    }
}

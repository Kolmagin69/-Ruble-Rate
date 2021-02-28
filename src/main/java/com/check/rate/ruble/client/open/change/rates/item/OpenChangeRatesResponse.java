package com.check.rate.ruble.client.open.change.rates.item;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenChangeRatesResponse {

    private long timestamp;

    private String base;

    private Map<String, Double> rates;

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

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(HashMap<String, Double> rates) {
        this.rates = rates;
    }

    public Map<ExchangeRate, Double> getActualRates() {
        if (rates == null)
            return null;
        final Map<ExchangeRate, Double> actualRates = new HashMap<>();
        for (Map.Entry<String, Double> entry : rates.entrySet()) {
            try {
                actualRates.put(ExchangeRate.valueOf(entry.getKey()), entry.getValue());
            } catch (IllegalArgumentException e){}
        }
        return actualRates;
    }
}

package com.check.rate.ruble.rest.item;

import com.check.rate.ruble.client.open.change.rates.item.ExchangeRate;

import java.time.LocalDate;

public class CheckCourseRequestBody {

    private String exc_rate;

    private String date;

    public String getExc_rate() {
        return exc_rate;
    }

    public void setExc_rate(String exc_rate) {
        this.exc_rate = exc_rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public CheckCourseRequestBody getDefault() {
        final CheckCourseRequestBody requestBody = new CheckCourseRequestBody();
        requestBody.setExc_rate(ExchangeRate.USD.toString());
        requestBody.setDate(LocalDate.now().minusDays(1).toString());
        return requestBody;
    }

}

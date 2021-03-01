package com.check.rate.ruble.rest;

import com.check.rate.ruble.client.open.change.rates.item.ExchangeRate;
import com.check.rate.ruble.exceptions.DateFormatException;
import com.check.rate.ruble.exceptions.RateNotSupportedException;
import com.check.rate.ruble.rest.item.CheckCourseRequestBody;
import com.check.rate.ruble.service.FeignClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("rub")
public class ChangesRubRateRestController {

    @Autowired
    private FeignClientService clientService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String firstPage(final Model model) {
        final CheckCourseRequestBody requestBody = new CheckCourseRequestBody();
        model.addAttribute("requestBody", requestBody.getDefault());
        final List<ExchangeRate> rates = Arrays.asList(ExchangeRate.values());
        model.addAttribute("rates", rates);
        return "first_page";
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public String checkExchangeRate(final Model model, @ModelAttribute("requestBody") CheckCourseRequestBody requestBody) {
        String giphyUrl;
        final List<ExchangeRate> rates = Arrays.asList(ExchangeRate.values());
        try {
            if(requestBody == null || requestBody.getDate() == null)
                throw new DateFormatException("Missing param value");
            if(requestBody.getExc_rate() == null)
                throw new IllegalArgumentException("Missing exc_rate param value");
            ExchangeRate exchangeRate = ExchangeRate.valueOf(requestBody.getExc_rate().toUpperCase());
            giphyUrl = clientService.checkRubleRateAndGetGif(exchangeRate, requestBody.getDate());
        } catch (DateFormatException e){
            model.addAttribute("rates", rates);
            model.addAttribute("requestBody", requestBody);
            return "error_date_page";
        } catch (IllegalArgumentException | RateNotSupportedException e) {
            model.addAttribute("rates", rates);
            model.addAttribute("requestBody", requestBody);
            return "error_rate_page";
        }
        model.addAttribute("giphyUrl", giphyUrl);
        model.addAttribute("rates", rates);
        model.addAttribute("requestBody", requestBody);
        return "home_page";
    }

}

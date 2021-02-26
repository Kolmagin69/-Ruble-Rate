package com.check.rate.ruble.rest;

import com.check.rate.ruble.client.open.change.rates.item.ExchangeRate;
import com.check.rate.ruble.exception.RateNotSupportedException;
import com.check.rate.ruble.rest.item.CheckCourseRequestBody;
import com.check.rate.ruble.service.FeignClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class RestController {

    @Autowired
    private FeignClientService clientService;

    @RequestMapping("/home")
    public String homePage(final Model model) {
        final CheckCourseRequestBody requestBody = new CheckCourseRequestBody();
        model.addAttribute("requestBody", requestBody.getDefault());
        final List<ExchangeRate> rates = Arrays.asList(ExchangeRate.values());
        model.addAttribute("rates", rates);
        return "first_home_page";
    }

    @RequestMapping("/check")
    public String checkCourse(final Model model, @ModelAttribute("requestBody") CheckCourseRequestBody requestBody) {
        String giphyUrl;
        final List<ExchangeRate> rates = Arrays.asList(ExchangeRate.values());
        try {
            ExchangeRate exchangeRate = ExchangeRate.valueOf(requestBody.getExc_rate().toUpperCase());
            giphyUrl = clientService.checkRubleRateAndGetGif(exchangeRate, requestBody.getDate());
        } catch (IllegalArgumentException e){
            model.addAttribute("rates", rates);
            model.addAttribute("requestBody", requestBody);
            return "error_date_home_page";
        } catch (RateNotSupportedException e) {
            model.addAttribute("rates", rates);
            model.addAttribute("requestBody", requestBody);
            return "error_rate_home_page";
        }
        model.addAttribute("giphyUrl", giphyUrl);
        model.addAttribute("rates", rates);
        model.addAttribute("requestBody", requestBody);
        return "home_page";
    }

}

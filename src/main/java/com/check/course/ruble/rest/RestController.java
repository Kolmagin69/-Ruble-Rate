package com.check.course.ruble.rest;

import com.check.course.ruble.client.open.change.rates.item.ExchangeRate;
import com.check.course.ruble.service.FeignClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RestController {

    @Autowired
    private FeignClientService clientService;

    @RequestMapping("/home")
    public String homePage() {
        return "first_home_page";
    }

    @RequestMapping("/check")
    public String checkCourse(final Model model,
                              @RequestParam String exc_rate,
                              @RequestParam String date) {
        final ExchangeRate exchangeRate = ExchangeRate.valueOf(exc_rate);
        final String giphyUrl = clientService.checkRubleRateAndGetGif(exchangeRate, date);
        model.addAttribute("giphyUrl", giphyUrl);
        return "home_page";
    }
}

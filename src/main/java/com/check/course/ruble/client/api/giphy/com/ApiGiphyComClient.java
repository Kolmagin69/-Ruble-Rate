package com.check.course.ruble.client.api.giphy.com;

import com.check.course.ruble.client.api.giphy.com.item.ApiGiphyComResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "apigiphycom",
        url = "https://api.giphy.com/v1/gifs/")
public interface ApiGiphyComClient {

    @RequestMapping(method = RequestMethod.GET,
            value = "/random")
    ApiGiphyComResponse getRandomGiphy(@RequestParam("api_key") String apiKey,
                                       @RequestParam("tag") String tag,
                                       @RequestParam("rating") String rating);

}

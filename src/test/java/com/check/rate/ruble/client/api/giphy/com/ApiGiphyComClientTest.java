package com.check.rate.ruble.client.api.giphy.com;

import com.check.rate.ruble.client.api.giphy.com.item.ApiGiphyComResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class ApiGiphyComClientTest {

    @MockBean
    private ApiGiphyComClient apiGiphyComClient;

    @Test
    void testGetRandomGift() {
        Mockito.when(apiGiphyComClient.getRandomGiphy(any(), any(), any()))
                .thenReturn(new ApiGiphyComResponse());
    }
}
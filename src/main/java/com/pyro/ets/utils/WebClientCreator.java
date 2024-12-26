package com.pyro.ets.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WebClientCreator {

    @Value("${receive.timeout}")
    private long receiveTimeout;

    @Value("${connection.timeout}")
    private long connectionTimeout;

    public WebClient getWebClient(String url) {
        return WebClient.builder()
            .baseUrl(url)
            .build();
    }

    public String send_post_url(String url, String strXML, String msisdn) {
        return WebClient.create(url)
            .post()
            .bodyValue(strXML)
            .header("Content-type", "application/xml")
            .header("charset", "UTF-8")
            .header("Connection", "close")
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }
}

package com.pyro.ets.utils;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pyro.bean.FetchbillReqBean;

@Service
public class RestWebClient {

    private static final Logger logger = LoggerFactory.getLogger(RestWebClient.class);
    private final RestTemplate restTemplate;

    public RestWebClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getObjMobRc(String url, String contimeout, String readTimeout, FetchbillReqBean o) {
        String ret = null;
        try {
            String req = "021234567" + "00000001" + "IVR" + "Jamal" + "3505" + "00000068";
            
            logger.info("URL to Send Request: {}", url);
            logger.info("Request Data: {}", req);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);

            HttpEntity<String> entity = new HttpEntity<>(req, headers);

            ret = restTemplate.postForObject(url, entity, String.class);
        } catch (Exception e) {
            logger.error("Error in getObjMobRc", e);
        }
        return ret;
    }
}

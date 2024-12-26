package com.pyro.ets.test;

import org.json.JSONObject;
import org.json.XML;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class XmlToBean {

    private static final Logger logger = LoggerFactory.getLogger(XmlToBean.class);

    public String convertXmlToJson(String xml) {
        try {
            JSONObject json = XML.toJSONObject(xml);
            String jsonString = json.toString();
            logger.info("Converted JSON: {}", jsonString);
            return jsonString;
        } catch (JSONException e) {
            logger.error("Error converting XML to JSON", e);
            return null;
        }
    }
}

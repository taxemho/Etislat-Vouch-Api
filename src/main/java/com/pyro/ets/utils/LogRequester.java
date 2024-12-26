package com.pyro.ets.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LogRequester {

    private static final Logger debugLogger = LoggerFactory.getLogger("debuglog");
    private static final Logger transLogger = LoggerFactory.getLogger("translog");
    private static final Logger exceptionLogger = LoggerFactory.getLogger("exceptionlog");
    private static final Logger mismatchLogger = LoggerFactory.getLogger("mismatchlog");
    private static final Logger queryLogger = LoggerFactory.getLogger("querylog");
    private static final Logger opt1Logger = LoggerFactory.getLogger("opt1log");

    public void writeLog(String body, String logname) {
        try {
            switch (logname.toLowerCase()) {
                case "debug":
                    debugLogger.debug(body);
                    break;
                case "trans":
                    transLogger.info(body);
                    break;
                case "exception":
                    exceptionLogger.error(body);
                    break;
                case "mismatch":
                    mismatchLogger.warn(body);
                    break;
                case "query":
                    queryLogger.info(body);
                    break;
                default:
                    opt1Logger.info(body);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

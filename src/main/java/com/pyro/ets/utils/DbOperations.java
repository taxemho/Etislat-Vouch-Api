package com.pyro.ets.utils;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import com.pyro.bean.SaveDataDetailsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DbOperations {

    private static final Logger logger = LoggerFactory.getLogger(DbOperations.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String insertTransaction(String query) {
        int result = 0;
        String resp;

        logger.info("Inserting Transaction....");
        logger.info("Inserting Query: {}", query);

        try {
            result = jdbcTemplate.update(query);
        } catch (Exception e) {
            logger.error("Error in Insert", e);
            return "{\"responseCode\":\"5000\",\"status\":\"FAILED\",\"responseMessage\":\"Internal Server Error\"}";
        }

        logger.info("Insert Count: {}", result);
        if (result >= 1) {
            resp = "{\"responseCode\":\"000\",\"status\":\"SUCCESS\",\"responseMessage\":\"Save Successful!\"}";
        } else {
            resp = "{\"responseCode\":\"5003\",\"status\":\"FAILED\",\"responseMessage\":\"Data Not Saved\"}";
        }
        return resp;
    }

    public SaveDataDetailsBean selectSaveData(String trid, String msisdn) {
        String selectquery = "select * from (select f.service_type,f.amountdue,s.amountpaid,s.servicekey,s.msisdn,f.nationalno,s.transactionid from "
                + " tr_table_etisalat_save s,tr_table_etisalat_fetch f where s.transactionid=f.transactionid and "
                + " msisdn=? and s.status=1 order by s.inti_time desc)a WHERE rownum <=1 ";

        SaveDataDetailsBean respbean = new SaveDataDetailsBean();

        try {
            logger.info("selectquery = {}", selectquery);
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(selectquery, msisdn);
            if (rowSet.next()) {
                respbean.setService_type(rowSet.getString("service_type"));
                respbean.setAmountdue(rowSet.getString("amountdue"));
                respbean.setAmountpaid(rowSet.getString("amountpaid"));
                respbean.setServicekey(rowSet.getString("servicekey"));
                respbean.setMsisdn(rowSet.getString("msisdn"));
                respbean.setNationalno(rowSet.getString("nationalno"));
                respbean.setTrid(rowSet.getString("transactionid"));
                respbean.setCode("000");
                String updatequery = "update tr_table_etisalat_save set status=0 where transactionid=?";
                updateTransaction(updatequery, respbean.getTrid());
            }
        } catch (Exception e) {
            logger.error("Error in selectSaveData", e);
            respbean.setCode("111");
        }

        logger.info("selectquery resp = {}", respbean);
        return respbean;
    }

    public String updateTransaction(String query, String... params) {
        int result = 0;
        String resp;

        logger.info("Updating Transaction....");
        logger.info("Updating Query: {}", query);

        try {
            result = jdbcTemplate.update(query, params);
        } catch (Exception e) {
            logger.error("Error in Update", e);
            return "{\"responseCode\":\"5000\",\"status\":\"FAILED\",\"responseMessage\":\"Internal Server Error\"}";
        }

        logger.info("Update Count: {}", result);
        if (result >= 1) {
            resp = "{\"responseCode\":\"000\",\"status\":\"SUCCESS\",\"responseMessage\":\"Update Successful!\"}";
        } else {
            resp = "{\"responseCode\":\"5003\",\"status\":\"FAILED\",\"responseMessage\":\"Data Not Updated\"}";
        }
        return resp;
    }
}

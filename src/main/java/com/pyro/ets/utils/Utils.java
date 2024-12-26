package com.pyro.ets.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class Utils {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public <T> T fromJson(String json, Class<T> clazz) throws Exception {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getWalletBalance(String srcmsisdn, String key, String walletType) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT TRUNC(DES3_INLINE_DECRYPT(AVAILABLE_BALANCE,?),2) FROM cm_tbl_wallet_m_details WHERE " +
                "wd_id=(SELECT wd_id FROM cm_tbl_wallet_details WHERE ch_id=(SELECT ch_id FROM cm_tbl_channel_auth_prof_det WHERE ch_device_id=?) AND wa_id =" +
                "(SELECT wa_id FROM wm_TBL_WALLET WHERE wa_ty_id=" +
                "(SELECT wa_ty_id FROM WM_TBL_WALLET_TYPE WHERE UPPER(WALLET_TYPE_NAME) =UPPER(?)))",
                new Object[]{key, srcmsisdn, walletType},
                String.class
            );
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    // Other methods...
}

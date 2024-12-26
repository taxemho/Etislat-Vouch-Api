package com.pyro.ets.utils;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

@Service
public class ServiceWalletInformation extends StoredProcedure {

    public ServiceWalletInformation(DataSource dataSource) {
        super(dataSource, "sp_servicewallet_upd");
        declareParameter(new SqlParameter("P_SPID", Types.NUMERIC));
        declareParameter(new SqlParameter("P_AMOUNT", Types.VARCHAR));
        declareParameter(new SqlParameter("P_KEY", Types.VARCHAR));
        declareParameter(new SqlParameter("P_TYPE", Types.NUMERIC));
        declareParameter(new SqlParameter("P_TRID", Types.VARCHAR));
        declareParameter(new SqlOutParameter("P_CODE", Types.NUMERIC));
        declareParameter(new SqlOutParameter("P_DESC", Types.VARCHAR));
        declareParameter(new SqlOutParameter("P_BAFOREBAL", Types.NUMERIC));
        declareParameter(new SqlOutParameter("P_AFTERBAL", Types.NUMERIC));
        compile();
    }

    public Map<String, Object> execute(String trid, long spid, String amount, String key, int type) {
        Map<String, Object> inputs = new HashMap<>();
        inputs.put("P_SPID", spid);
        inputs.put("P_AMOUNT", amount);
        inputs.put("P_KEY", key);
        inputs.put("P_TYPE", type);
        inputs.put("P_TRID", trid);

        try {
            return execute(inputs);
        } catch (Exception e) {
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("P_CODE", -1);
            errorMap.put("P_DESC", e.getMessage());
            errorMap.put("P_BAFOREBAL", 0.0);
            errorMap.put("P_AFTERBAL", 0.0);
            return errorMap;
        }
    }
}

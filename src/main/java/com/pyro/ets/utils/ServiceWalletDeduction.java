package com.pyro.ets.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Map;

import com.corereqbeans.Corerequest;
import com.corereqbeans.WalletInfo;
import com.pyro.security.EncryptDecryptWalletAmount;

@Service
public class ServiceWalletDeduction {

    @Autowired
    private ServiceWalletInformation serviceWalletInformation;

    public WalletInfo updateServiceWallet(Corerequest req, DataSource ds) {
        WalletInfo ret = new WalletInfo();
        try {
            String key1 = "1234567890";
            String key = EncryptDecryptWalletAmount.getAmountEncKey(key1);
            int type = Utils.validateInt(req.getWalletoperationtype());

            ret.setTypeofreq(type);
            ret.setServicewallettransAmount(Double.valueOf(Utils.validateDouble(req.getActualamountdeducted())));

            Map<String, Object> map = serviceWalletInformation.execute(
                Utils.validateStr(req.getRequesttrid()),
                Utils.validateLong(req.getSpid()),
                Utils.validateStr(req.getActualamountdeducted()),
                key,
                type
            );

            if (map != null && !map.isEmpty()) {
                ret.setCode(Utils.validateStr(map.get("P_CODE")));
                ret.setDesc(Utils.validateStr(map.get("P_DESC")));
                ret.setServicewalletBalanceBefore(Double.valueOf(Utils.validateDouble(map.get("P_BAFOREBAL"))));
                ret.setServicewalletBalanceAfter(Double.valueOf(Utils.validateDouble(map.get("P_AFTERBAL"))));
            } else {
                ret.setDesc("");
            }
        } catch (Exception e) {
            // Log the exception
        }
        return ret;
    }
}

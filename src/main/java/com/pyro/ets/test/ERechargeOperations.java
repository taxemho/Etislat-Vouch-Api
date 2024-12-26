/*package com.pyro.ets.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

import org.json.simple.JSONObject;

@Service
public class ERechargeService {
	

	  public String processURLinput(String hitUrl, JSONObject inReq) {
	    String url = hitUrl;
	    String tid = "";
	    int responseCode = 0;
	    
	   // socketReader = null;
	    HttpsURLConnection httpsConnection = null;
	   // log.info("ERechargeOperations: processURLinput(): URL received by eRecharge Interface=" + url);
	    
	    if (url.contains("htt")) {
	      BufferedReader socketReader;
		try {
	      //  log.info("ERechargeOperations: processURLinput(): Opening URL Connection to IN...");
	        
	        httpsConnection = (HttpsURLConnection)(new URL(url)).openConnection();
	        httpsConnection.setReadTimeout(12000);
	        SSLSocketFactory sslsocketfactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
	        httpsConnection.setHostnameVerifier(new HostnameVerifier() {
	              public boolean verify(String hostname, SSLSession session) {
	                if (hostname.equals(""))
	                  return true; 
	                return false;
	              }
	            });
	        httpsConnection.setSSLSocketFactory(sslsocketfactory);
	        
	        httpsConnection.setDoOutput(true);
	        httpsConnection.setDoInput(true);
	        httpsConnection.setRequestProperty("Content-Type", "application/json");
	        httpsConnection.setRequestProperty("Accept", "application/json");
	        httpsConnection.setRequestMethod("POST");
	        httpsConnection.setReadTimeout(12000);
	        httpsConnection.setSSLSocketFactory(sslsocketfactory);
	        httpsConnection.setAllowUserInteraction(true);
	        
	        httpsConnection.setRequestProperty("Content-Type", "application/json");

	        
	        httpsConnection.setRequestProperty("Cache-Control", "no-cache");







	        
	        long ONE_MINUTE_IN_MILLIS = 60000L;
	        Calendar date = Calendar.getInstance();
	        long t = date.getTimeInMillis();
	        Date today = new Date(t - 2 * 60000L);
	        SimpleDateFormat sfmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	        String todays = sfmt.format(today);
	       // log.info("Server Request time with format :: " + todays);
	     //   byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyString);
	      //  byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);


	        
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        
	        EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
	        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
	        
	        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
	        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
	        
	        Signature signature = Signature.getInstance("SHA256withRSA");
	        
	        String payload = "POST" + url.substring(url.indexOf("/restcon/"), url.length()) + inReq.toString() + 
	          "secretWord=" + AppConfigLoader.SECRET + "api-request-merchantid" + AppConfigLoader.merchantIDs + 
	          "api-request-timestamp" + todays;

	        
	        signature.initSign(privateKey);
	        
	        signature.update(payload.getBytes());
	        byte[] signatureBytes = signature.sign();
	        
	  //      String sign = Base64.getEncoder().encodeToString(signatureBytes);
	        
	    //    httpsConnection.setRequestProperty("Api-Request-Channel", "MERCHANT_POS");
	      //  httpsConnection.setRequestProperty("Api-Request-MerchantId", AppConfigLoader.merchantIDs);
	        //httpsConnection.setRequestProperty("Api-Request-Timestamp", todays);
	        //httpsConnection.setRequestProperty("Api-Operation-Signature", sign.replaceAll("[\n\r]", ""));
	        
	        OutputStreamWriter wr = new OutputStreamWriter(httpsConnection.getOutputStream());
	        wr.write(inReq.toString());
	        wr.flush();
	        
	        log.info(
	            "ERechargeOperations: processURLinput(): Open URL Connection to IN: SUCCESS :: Response Code :" + 
	            httpsConnection.getResponseCode() + " Response Message : " + 
	            httpsConnection.getResponseMessage());
	        
	        responseCode = httpsConnection.getResponseCode();
	        
	        if (httpsConnection.getResponseCode() == ERechargeConstants.HTTP_OK) {
	          socketReader = new BufferedReader(new InputStreamReader(httpsConnection.getInputStream()));
	        } else {
	          socketReader = new BufferedReader(new InputStreamReader(httpsConnection.getErrorStream()));
	        } 
	        StringBuilder input = new StringBuilder();
	        String line = "";
	        
	        while ((line = socketReader.readLine()) != null) {
	          input.append(line);
	        }
	        
	        line = input.toString();


	        
	     //   log.info("The Response for ERecharge :\n" + line);
	        
	        return String.valueOf(responseCode) + "|" + line;
	      }
	      catch (SocketTimeoutException e) {
	      //  log.info("ERechargeOperations: processURLinput(): Socket TimedOut; EXCEPTION=" + e.getMessage());
	        return String.valueOf(responseCode) + "|{\"tid\":" + tid + 
	          ",\"status\":\"Exception\",\"error_code\":\"1\",\"message\":\"Server Internal Error\"}";
	      } catch (Exception ex) {
	      //  log.info("ERechargeOperations: processURLinput(): Read socket data failed on IN; EXCEPTION=" + 
	            ex.getMessage();
	        return String.valueOf(responseCode) + "|{\"tid\":" + tid + 
	          ",\"status\":\"Exception\",\"error_code\":\"1\",\"message\":\"Server Internal Error\"}";
	      } finally {
	        if (socketReader != null) {
	          try {
	            socketReader.close();
	          } catch (IOException e) {
	            e.printStackTrace();
	          } 
	        }
	      } 
	    }
	    return String.valueOf(responseCode) + "|{\"tid\":" + tid + 
	      ",\"status\":\"INVALID\",\"error_code\":\"404\",\"message\":\"Server Internal Error\"}";
	  }

}
*/
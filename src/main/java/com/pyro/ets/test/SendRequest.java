package com.pyro.ets.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SendRequest {

    @Value("${certificate.file.path}")
    private String certificateFilePath;

    @Value("${certificate.password}")
    private String certificatePassword;

    public String callWebservice(String webserviceUrl, String requestData) {
        String line, strbuf = "";
        HttpsURLConnection con = null;
        OutputStreamWriter dos = null;
        InputStreamReader dis = null;

        try {
            char[] passw = certificatePassword.toCharArray();
            KeyStore ks = KeyStore.getInstance("PKCS12");
            try (FileInputStream keyInput = new FileInputStream(certificateFilePath)) {
                ks.load(keyInput, passw);
            }

            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, passw);

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());
            SSLContext.setDefault(sslContext);
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

            URL url = new URL(webserviceUrl);
            con = (HttpsURLConnection) url.openConnection();

            if (con != null) {
                configureConnection(con);
                dos = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
                dos.write(requestData);
                dos.flush();

                dis = new InputStreamReader(con.getInputStream(), "UTF-8");
                if (dis != null) {
                    try (BufferedReader reader = new BufferedReader(dis)) {
                        if ((line = reader.readLine()) != null) {
                            strbuf = line;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            // Consider using a logging framework instead of System.out.println
            ex.printStackTrace();
        } finally {
            closeStreams(dos, dis);
        }

        return strbuf;
    }

    private void configureConnection(HttpsURLConnection con) throws Exception {
        con.setRequestProperty("Content-Type", "application/xml;charset=utf-8");
        con.setRequestProperty("Accept", "application/xml");
        con.setRequestProperty("Accept-Charset", "UTF-8");
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setUseCaches(false);
        con.setDefaultUseCaches(false);
        con.setRequestMethod("POST");
        con.connect();
    }

    private void closeStreams(OutputStreamWriter dos, InputStreamReader dis) {
        try {
            if (dos != null) dos.close();
            if (dis != null) dis.close();
        } catch (Exception ex) {
            // Consider using a logging framework instead of System.out.println
            ex.printStackTrace();
        }
    }
}

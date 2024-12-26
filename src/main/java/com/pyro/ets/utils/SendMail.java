package com.pyro.ets.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class SendMail {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SendRequest sendRequest;

    @Value("${bank.id}")
    private String bankId;

    @Value("${file.path}")
    private String filePath;

    @Value("${cdr.file.url}")
    private String cdrFileUrl;

    public String doSaveAsCSV() throws Exception {
        String strDate = new SimpleDateFormat("ddMMyy").format(new Date());
        String filename = bankId + "-" + strDate + ".txt";
        String fullFilePath = filePath + "/" + filename;

        String status = generateDataFile(filename);
        String[] statusArr = status.split(",");

        if (statusArr[0].equalsIgnoreCase("failed")) {
            return "File could not be downloaded";
        }

        File file = new File(fullFilePath);
        String encodedData = encodeFileToBase64(file);

        String url = cdrFileUrl.replace("@#stype@#", statusArr[1])
                               .replace("@#docname@#", filename)
                               .replace("@#docdata@#", encodedData);

        String resp = sendRequest.sendGET(url);
        return "SUCCESS";
    }

    private String generateDataFile(String filename) {
        String query = "SELECT TRID, VENDOR_TRID, p.nationalno, SERVICETYPE, AMOUNTPAID, t.TRANSDATESTAMP as transactiondatestamp, ch_name " +
                       "FROM tr_table_ethisalat_post p, CM_TBL_PERSONAL_DETAILS c, tr_table_etisalat_fetch t " +
                       "WHERE CH_MOBILE_NUMBER = msisdn AND t.transactionid = p.vendor_trid " +
                       "AND trunc(p.inti_time) = trunc(sysdate-1) AND status = 2";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);

        try (FileWriter fw = new FileWriter(filePath + "/" + filename)) {
            writeHeader(fw);
            for (Map<String, Object> row : rows) {
                writeRow(fw, row);
            }
            return "SUCCESS," + rows.get(0).get("SERVICETYPE");
        } catch (Exception e) {
            return "FAILED";
        }
    }

    private void writeHeader(FileWriter fw) throws IOException {
        List<String> headers = getHeaderArray();
        fw.write(String.join(",", headers) + "\n");
    }

    private void writeRow(FileWriter fw, Map<String, Object> row) throws IOException {
        // Write row data
    }

    private List<String> getHeaderArray() {
        List<String> headers = new ArrayList<>();
        headers.add("CustomerID");
        headers.add("BankID");
        // Add other headers
        return headers;
    }

    private String encodeFileToBase64(File file) throws IOException {
        byte[] fileContent = Files.readAllBytes(file.toPath());
        return Base64.getEncoder().encodeToString(fileContent);
    }
}

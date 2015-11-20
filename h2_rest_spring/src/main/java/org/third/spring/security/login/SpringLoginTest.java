package org.third.spring.security.login;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpringLoginTest {
    static final Logger logger = LoggerFactory.getLogger(SpringLoginTest.class);

    public static final void doGet() {
        try {
            java.net.URL url = new URL("http://localhost:8180/webtest/admin");
            java.net.HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // connection.setDoOutput(true);// required for POST
            // connection.setUseCaches(false);// required for POST
            // instead of a GET, we're going to send using method="POST"
            connection.setRequestMethod("GET");

            // we are allowing default host name validation.We need to add an
            // option to disable it if the customer so wishes.

            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                logger.trace("Connection is successful:" + connection.getResponseCode());
            } else {
                logger.error("Connection response code returned :" + connection.getResponseCode());
                // obtain meaningful error information
                StringBuilder errBuffer = new StringBuilder();
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null)
                    errBuffer.append(errorLine);

                logger.error("Connection response code returned: " + connection.getResponseCode() + ": "
                        + errBuffer.toString());
            }

            StringBuilder response = new StringBuilder();
            String respEnc = connection.getContentEncoding();
            InputStream is;
            if (respEnc != null && respEnc.equalsIgnoreCase("gzip")) {
                is = new GZIPInputStream(connection.getInputStream());
            } else {
                is = new BufferedInputStream(connection.getInputStream());
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String li;
            while ((li = reader.readLine()) != null) {
                response.append(li);
            }
            System.out.println(response.toString());
            // Set basic authorization, if integration account username and
            // password are
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // doGet();
        doPost();
    }

    private static final void fillWithForUrlencoded(java.net.HttpURLConnection hc, Map<String, String> parameterMap)
            throws IOException {
        hc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        // username=rod&password=koala&submit=Login&_csrf=7f3b423e-a452-43a8-8197-16879d76fd73
        StringBuffer resSB = new StringBuffer("\r\n");
        boolean first = true;
        for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
            if (first) {
                first = false;
            } else {
                resSB.append("&");
            }
            resSB.append(entry.getKey()).append("=").append(entry.getValue());
        }
        PrintWriter out = new PrintWriter(hc.getOutputStream());
        out.write(resSB.toString());
        out.flush();
        out.close();
    }

    private static final void fillWithForMultipart(java.net.HttpURLConnection hc) {
        // String BOUNDARY = "----MyFormBoundarySMFEtUYQG6r5B920";
        // hc.setRequestProperty("Content-Type", "multipart/form-data;
        // boundary=" + BOUNDARY);
        //
        // String PREFIX = "--" , LINEND = "\r\n" ;
        // StringBuffer resSB = new StringBuffer("\r\n");
        // String endBoundary = "\r\n--" + BOUNDARY + "--\r\n";
        // for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
        // resSB.append(PREFIX);
        // resSB.append(BOUNDARY);
        // resSB.append(LINEND);
        // resSB.append("Content-Disposition: form-data; name=\"" +
        // entry.getKey() +"\"" + LINEND);
        //// sb.append("Content-Type: text/plain; charset=UTF-8" + LINEND);
        //// sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
        // resSB.append(LINEND);
        // resSB.append(entry.getValue());
        // resSB.append(LINEND);
        //// resSB.append("--").append(BOUNDARY).append("\r\n");
        // }
        //
        //
        // // 请求结束标志
        //
        // out.write("--" + BOUNDARY);
        // out.write( resSB.toString());
        // out.write(endBoundary);
        // out.flush();
        // out.close();
    }

    static class myX509TrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType) {
            System.out.println("cert: " + chain[0].toString() + ", authType: " + authType);
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) {
            System.out.println("cert: " + chain[0].toString() + ", authType: " + authType);
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    static class myHostnameVerifier implements HostnameVerifier {

        public boolean verify(String hostname, SSLSession session) {
            System.out.println("Warning: URL Host: " + hostname + " vs. " + session.getPeerHost());
            return true;
        }
    }

    public static void doPostInSSL() {
        myX509TrustManager xtm = new myX509TrustManager();
        myHostnameVerifier hnv = new myHostnameVerifier();
        try {
            javax.net.ssl.SSLContext sslContext = null;
            try {
                sslContext = javax.net.ssl.SSLContext.getInstance("TLS"); // 或SSL
                javax.net.ssl.X509TrustManager[] xtmArray = new javax.net.ssl.X509TrustManager[] { xtm };
                sslContext.init(null, xtmArray, new java.security.SecureRandom());
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
            if (sslContext != null) {
                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            }
            HttpsURLConnection.setDefaultHostnameVerifier(hnv);
            // private String url =
            // "https://esales.the9.com/esa/DealerLogin.php?txt_sLogin=andysmile234&pas_sPwd=343211&userstatus=1";
            java.net.URL url = new URL("http://localhost:18180/webtest/login.do");
            javax.net.ssl.HttpsURLConnection hc = (HttpsURLConnection) url.openConnection();
            hc.setDoOutput(true);
            hc.setDoInput(true);
            hc.setUseCaches(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void doPost() {
        try {
            java.net.URL url = new URL("http://localhost:18180/webtest/login.do");
            java.net.HttpURLConnection hc = (HttpURLConnection) url.openConnection();
            hc.setDoOutput(true);
            hc.setDoInput(true);
            hc.setUseCaches(false);
            hc.setRequestMethod("POST");
            hc.setRequestProperty("Accept", "application/json");
            hc.setRequestProperty("Accept-Encoding", "gzip, deflate");
            hc.setRequestProperty("Charsert", "UTF-8");

            Map<String, String> parameterMap = new LinkedHashMap<>();
            parameterMap.put("username", "rod");
            parameterMap.put("password", "koala");
            parameterMap.put("_csrf", "6d69f9b1-89e4-41b4-811e-bb1d4fec966a");
            parameterMap.put("submit", "Login");

            fillWithForUrlencoded(hc, parameterMap);

            if (hc.getResponseCode() == HttpURLConnection.HTTP_OK) {
                logger.trace("Connection is successful:" + hc.getResponseCode());
            } else {
                logger.error("Connection response code returned :" + hc.getResponseCode());
                // obtain meaningful error information
                StringBuilder errBuffer = new StringBuilder();
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(hc.getErrorStream()));
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null)
                    errBuffer.append(errorLine);

                logger.error(
                        "Connection response code returned: " + hc.getResponseCode() + ": " + errBuffer.toString());
            }

            StringBuilder response = new StringBuilder();
            String respEnc = hc.getContentEncoding();
            InputStream is;
            if (respEnc != null && respEnc.equalsIgnoreCase("gzip")) {
                is = new GZIPInputStream(hc.getInputStream());
            } else {
                is = new BufferedInputStream(hc.getInputStream());
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String li;
            while ((li = reader.readLine()) != null) {
                response.append(li);
            }
            System.out.println(response.toString());
            // Set basic authorization, if integration account username and
            // password are
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

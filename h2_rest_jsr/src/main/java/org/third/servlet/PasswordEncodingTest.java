package org.third.servlet;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordEncodingTest {
    public static final String UTF8 = "UTF-8";

    public static void main(String[] args) {
//        integrationusers.properties  idmTransportUser/idmTransportUser
//        admin=ENC(M2SJzyx2nyYLenHsR9gpQImsSZXU9uogyCG8MZJM8t2ruzFyjzW0gt2/ykUhTRK1d8gTsxETzi8=)
        String username = "idmTransportUser", password = "idmTransportUser";
        String authHeader = username + ":" + password;
        try {
            String encoded = Base64.getEncoder().encodeToString(authHeader.getBytes(UTF8));
            System.out.println(encoded);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // connection.setRequestProperty("Authorization", "Basic " + encoded);
    }

    static String md5Hex(String data) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }
        return new String(org.springframework.security.crypto.codec.Hex.encode(digest.digest(data.getBytes())));
    }
}

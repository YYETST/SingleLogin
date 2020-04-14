package org.example.utils;

import java.security.MessageDigest;
import java.util.Arrays;


public class SHASignUtil {


    public static String getSHA1(String... password) throws CryptoException {
        try {
            StringBuffer sb = new StringBuffer();
            // 字符串排序
            Arrays.sort(password);
            for (int i = 0; i < password.length; i++) {
                sb.append(password[i]);
            }
            String str = sb.toString();
            // SHA1签名生成
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();

            StringBuffer hexstr = new StringBuffer();
            String shaHex = "";
            for (int i = 0; i < digest.length; i++) {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
            return hexstr.toString();
        } catch (Exception e) {
            throw new CryptoException(ErrorCode.SHA1_SIGN_FAILED, e);
        }
    }
}

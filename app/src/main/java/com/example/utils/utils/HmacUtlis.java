package com.example.utils.utils;

import android.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

class HmacUtlis {
    public static String HMAC_SHA(String secret, String message) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] encode = Base64.encode(sha256_HMAC.doFinal(message.getBytes()), Base64.DEFAULT);
            String hash = new String(encode, 0, 0, encode.length).trim();
            return hash;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

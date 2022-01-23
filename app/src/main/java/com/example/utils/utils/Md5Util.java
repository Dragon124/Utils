package com.example.utils.utils;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/11/29.
 */

public class Md5Util {

    private static final char[] HEX_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
            'f' };

    public static char[] encodeHex(byte[] bytes) {
        char[] chars = new char[32];
        for (int i = 0; i < chars.length; i += 2) {
            byte b = bytes[(i / 2)];
            chars[i] = HEX_CHARS[(b >>> 4 & 0xF)];
            chars[(i + 1)] = HEX_CHARS[(b & 0xF)];
        }
        return chars;
    }

    public static MessageDigest getDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("Could not find MessageDigest with algorithm \"" + algorithm + "\"", ex);
        }
    }

    public static String md5(String src) {
        MessageDigest md5 = getDigest("MD5");
        return new String(encodeHex(md5.digest(src.getBytes())));
    }

    public static String toSign(Map<String,Object> map) {

        return "";
    };

    public static String getFormatParams(Map<String, Object> params,boolean flag) throws Exception {
        List<Map.Entry<String, Object>> infoIds =
                new ArrayList<Map.Entry<String, Object>>((Collection<? extends Map.Entry<String, Object>>) params.entrySet());
        Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
            public int compare(Map.Entry<String, Object> arg0, Map.Entry<String, Object> arg1) {
                return (arg0.getKey()).compareTo(arg1.getKey());
            }
        });
        String ret = "";

        for (Map.Entry<String, Object> entry : infoIds) {
            ret += entry.getKey();
            ret += "=";
            if(flag) {
                if(generateJudgment(entry.getValue().toString())) {
                    ret += URLEncoder.encode(entry.getValue().toString(), "UTF-8") ;
                }else {
                    ret += entry.getValue().toString();
                }
            }else {
                ret += entry.getValue().toString();
            }
            ret += "&";
        }
        ret = ret.substring(0, ret.length() - 1);
        return ret;
    }

    //判断是否有汉字
    public static boolean generateJudgment(String countname){
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher isNum = p.matcher(countname);
        if (isNum.find()) {
            return true;
        }
        return false;
    }

}

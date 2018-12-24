package com.vince.tools.messagedemo.utils;

import sun.misc.BASE64Decoder;

import java.io.UnsupportedEncodingException;

/**
 * base64 编码、解码util
 */
public class Base64Util {
    /**
     * 将 s 进行 BASE64 编码
     */
    public static String encode(String s) {
        if (s == null) {
            return null;
        }else {
            s = "vince"+ DateUtils.getgetCurrentDateYMD();
        }
        String res = "";
        try {
            res = new sun.misc.BASE64Encoder().encode(s.getBytes("utf8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 将 BASE64 编码的字符串 s 进行解码
     */
    public static String decode(String s) {
        if (s == null){
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b,"utf8");
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 测试
     */
    public static void main(String[] args) {
        System.out.println(Base64Util.encode("zcsdfs"));
        System.out.println(Base64Util.decode("dmluY2UyMDE4MTIyMw=="));

    }

}
package com.iss.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {


    /**
     * md5加密
     * @param str 需要md5加密的信息
     * @return 加密后的md5字符串
     */
    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();

            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();
        /*
        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        // 16位加密，从第9位到25位
        return md5StrBuff.substring(8, 24).toString().toUpperCase();
        */
        return byte2hex(byteArray);
    }

//    public static void main(String[] args) {
//        
//        Md5Util my = new Md5Util();
//        my.testDigest();
//    }

//    public void testDigest() {
//        try {
//            String myinfo = "我的测试信息";
//
//             java.security.MessageDigest
//             alga=java.security.MessageDigest.getInstance("MD5");
////            MessageDigest alga = MessageDigest.getInstance("SHA-1");
//            alga.update(myinfo.getBytes());
//            byte[] digesta = alga.digest();
//            System.out.println("本信息摘要是:" + byte2hex(digesta));
//            // 通过某种方式传给其他人你的信息(myinfo)和摘要(digesta) 对方可以判断是否更改或传输正常
//            MessageDigest algb = MessageDigest.getInstance("SHA-1");
//            algb.update(myinfo.getBytes());
//            if (algb.isEqual(digesta, algb.digest())) {
//                System.out.println("信息检查正常");
//            } else {
//                System.out.println("摘要不相同");
//            }
//
//        } catch (java.security.NoSuchAlgorithmException ex) {
//            System.out.println("非法摘要算法");
//        }
//
//    }

    /**
     *  二行制转字符串
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b)
    {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
//        return hs.toUpperCase();
        return hs.toLowerCase();
    }

}

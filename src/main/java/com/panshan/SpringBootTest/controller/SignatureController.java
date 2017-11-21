package com.panshan.SpringBootTest.controller;

import com.panshan.SpringBootTest.entity.Signature;
import com.panshan.SpringBootTest.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Timestamp;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by panshan on 2017/7/11.
 */
@RestController
@RequestMapping(value = "/signature")
public class SignatureController {
    @Autowired
    RedisService redisService;
    private final String secret = "f02778b0e5f06028634ea374fd5cf7c8";
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test(HttpServletRequest request){

        Signature signature = new Signature();
        signature.setRand(request.getParameter("rand"));
        signature.setSign(request.getParameter("sign"));
        signature.setTimestamp(Long.parseLong(request.getParameter("timestamp")));
        if (!validateTime(signature.getTimestamp())){
            return "时间戳错误,请求已超时";
        }
        if (redisService.isKeyExist(signature.getRand())){
            return "重复请求";
        }else
        {
            redisService.setKey(signature.getRand());
        }
        //验证md5啦,需要先把request的所有url参数放到hashMap，对hashMap进行排序。最后再进行md5加密
        HashMap map = requestToMap(request);
        map.remove("sign");
        String temp = MapToString(map, secret);
        String md5Value = "";
        try{
             md5Value = EncoderByMd5(temp);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("md5Value:"+md5Value);
        return md5Value;
    }
    public boolean validateTime(long requestTime){
        long systemTime = System.currentTimeMillis()/1000;
        if (systemTime-requestTime>=0&&systemTime-requestTime<120){
            return true;
        }
        return false;
    }
    /**
     * 这里只获取了parameter的值
     * @param request
     * @return
     */
    public HashMap requestToMap(HttpServletRequest request){
        Enumeration enu = request.getParameterNames();
        HashMap hashMap = new HashMap();
        while(enu.hasMoreElements()){
            String key = (String) enu.nextElement();
            String value = request.getParameter(key);
            hashMap.put(key,value);
        }
        return hashMap;
    }

    /**
     * HashMap排序并转换成String，String前后端加上secret。
     * @param hashMap
     * @param secret
     * @return
     */
    public String MapToString(HashMap hashMap,String secret){
        String result = secret;
        Object[] key_arr = hashMap.keySet().toArray();
        Arrays.sort(key_arr);
        for (Object key :key_arr){
            String temp = key+"="+hashMap.get(key);
            result= result+temp;
        }
        result = result+ secret;
        System.out.println("the string is "+ result);
        return result;
    }

    /**利用MD5进行加密
     * @param str  待加密的字符串
     * @return  加密后的字符串
     * @throws NoSuchAlgorithmException  没有这种产生消息摘要的算法
     * @throws UnsupportedEncodingException
     */
    public String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5= MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }
}

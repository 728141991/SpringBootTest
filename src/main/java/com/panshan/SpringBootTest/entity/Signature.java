package com.panshan.SpringBootTest.entity;

import java.security.Timestamp;

/**
 * Created by panshan on 2017/7/11.
 */
public class Signature {

    public long timestamp;
    public String rand;
    public String sign;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getRand() {
        return rand;
    }

    public void setRand(String rand) {
        this.rand = rand;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }


}

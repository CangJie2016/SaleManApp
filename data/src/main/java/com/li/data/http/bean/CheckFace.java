package com.li.data.http.bean;

/**
 * Created by 李振强 on 2017/11/28.
 */

public class CheckFace {
    private String faceToken;
    private int lastRecordId;

    public String getFaceToken() {
        return faceToken;
    }

    public void setFaceToken(String faceToken) {
        this.faceToken = faceToken;
    }

    public int getLastRecordId() {
        return lastRecordId;
    }

    public void setLastRecordId(int lastRecordId) {
        this.lastRecordId = lastRecordId;
    }
}

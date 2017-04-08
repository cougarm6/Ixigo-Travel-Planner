package com.kungfupandas.ixigotripplanner.pojo;

/**
 * Created by tushar on 08/04/17.
 */

public class NetworkException extends Exception {
    private String reason;
    private Integer responseCode;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public NetworkException(String reason, Integer responseCode) {
        super(reason);
        this.responseCode = responseCode;
        this.reason = reason;

    }
}

package com.hoangtuthinhthao.languru.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Session {

    @SerializedName("createdAt")
    @Expose
    private Long createdAt;
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("client_type")
    @Expose
    private String clientType;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("refresh_token")
    @Expose
    private String refreshToken;

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}


package com.hoangtuthinhthao.languru.models.io;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class IoResponse implements Serializable {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("payload")
    @Expose
    private Payload payload;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

}

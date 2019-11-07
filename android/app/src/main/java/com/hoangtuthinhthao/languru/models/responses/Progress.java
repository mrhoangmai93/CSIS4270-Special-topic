package com.hoangtuthinhthao.languru.models.responses;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Progress implements Serializable {

    @SerializedName("topic")
    @Expose
    private String topic;
    @SerializedName("progress")
    @Expose
    private String progress;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

}

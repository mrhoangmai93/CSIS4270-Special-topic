
package com.hoangtuthinhthao.languru.models.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Topic implements Serializable {

    @SerializedName("topic")
    @Expose
    private String topic;
    @SerializedName("progress")
    @Expose
    private String progress;
    @SerializedName("list")
    @Expose
    private List<Lesson> list = null;

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

    public List<Lesson> getList() {
        return list;
    }

    public void setList(List<Lesson> list) {
        this.list = list;
    }

}

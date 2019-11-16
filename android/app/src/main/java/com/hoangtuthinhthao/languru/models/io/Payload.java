
package com.hoangtuthinhthao.languru.models.io;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Payload implements Serializable {

    @SerializedName("readyToPlay")
    @Expose
    private Boolean readyToPlay;
    @SerializedName("finished")
    @Expose
    private Boolean finished;
    @SerializedName("currentLevel")
    @Expose
    private Integer currentLevel;
    @SerializedName("matchCount")
    @Expose
    private Integer matchCount;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("socketId")
    @Expose
    private String socketId;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public Boolean getReadyToPlay() {
        return readyToPlay;
    }

    public void setReadyToPlay(Boolean readyToPlay) {
        this.readyToPlay = readyToPlay;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Integer getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Integer currentLevel) {
        this.currentLevel = currentLevel;
    }

    public Integer getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(Integer matchCount) {
        this.matchCount = matchCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}

package com.hoangtuthinhthao.languru.controllers.loadServices.topic;

import com.hoangtuthinhthao.languru.models.responses.Lesson;
import com.hoangtuthinhthao.languru.models.responses.Topic;

import java.util.ArrayList;

public interface LoadTopicCallback {
    void successLoadLesson(String topicName, Topic topic);

    void failLoadLesson(String message);

}

package com.hoangtuthinhthao.languru.controllers.loadServices.lesson;

import com.hoangtuthinhthao.languru.models.responses.Lesson;

import java.util.ArrayList;

public interface LoadLessonCallback {
    void successLoadLesson(String topicName, ArrayList<Lesson> lessons);

    void failLoadLesson(String message);

}

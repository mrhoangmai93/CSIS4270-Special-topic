package com.hoangtuthinhthao.languru.controllers.api;

import com.hoangtuthinhthao.languru.models.responses.Lesson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ApiLessonService {

    @GET("/v1/lesson/list/byTopic/{topicName}")
    Call<ArrayList<Lesson>> getByTopic(@Path("topicName") String topicName);
}

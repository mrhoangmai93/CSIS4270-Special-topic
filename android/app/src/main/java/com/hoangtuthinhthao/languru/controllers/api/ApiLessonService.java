package com.hoangtuthinhthao.languru.controllers.api;

import com.hoangtuthinhthao.languru.models.responses.Topic;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiLessonService {

//    @GET("/v1/lesson/list/byTopic/{topicName}")
//    Call<ArrayList<Lesson>> getByTopic(@Path("topicName") String topicName);
    @GET("/v1/lesson/list/byTopic/{topicName}")
   Call<Topic> getByTopic(@Path("topicName") String topicName);
}

package com.hoangtuthinhthao.languru.controllers.api;

import com.hoangtuthinhthao.languru.models.responses.Lesson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiGameService {

    @GET("/v1/lesson/list/random/{numberWord}")
    Call<ArrayList<Lesson>> getByNumberWord(@Path("numberWord") int numberWord);
}

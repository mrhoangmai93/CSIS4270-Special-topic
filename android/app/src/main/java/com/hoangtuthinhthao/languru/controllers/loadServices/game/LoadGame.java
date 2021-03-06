package com.hoangtuthinhthao.languru.controllers.loadServices.game;

import android.content.Context;

import com.hoangtuthinhthao.languru.controllers.api.ApiClient;
import com.hoangtuthinhthao.languru.controllers.api.ApiGameService;
import com.hoangtuthinhthao.languru.controllers.loadServices.topic.LoadTopicCallback;
import com.hoangtuthinhthao.languru.models.game.LoadGameCallback;
import com.hoangtuthinhthao.languru.models.responses.Lesson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadGame {
    private static ApiGameService apiInterface;
    private Context context;

    public LoadGame(Context context) {
        this.context = context;
        apiInterface = ApiClient.getClient(this.context).create(ApiGameService.class);
    }

    public void byNumberWord(final int numberWord, final LoadGameCallback callback) {
        Call<ArrayList<Lesson>> call = apiInterface.getByNumberWord(numberWord);
        call.enqueue(new Callback<ArrayList<Lesson>>() {
            @Override
            public void onResponse(Call<ArrayList<Lesson>> call, Response<ArrayList<Lesson>> response) {
                if(response.isSuccessful()){
                    ArrayList<Lesson> lessonList = response.body(); // have your all data

                    callback.successLoadGame(numberWord,lessonList);

                } else {

                    callback.failLoadGame(response.errorBody().toString());

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Lesson>> call, Throwable t) {
                callback.failLoadGame(t.getMessage());
            }

        });
    }
}

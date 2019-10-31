package com.hoangtuthinhthao.languru.controllers.loadServices;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hoangtuthinhthao.languru.controllers.api.ApiClient;
import com.hoangtuthinhthao.languru.controllers.api.ApiLessonService;
import com.hoangtuthinhthao.languru.models.responses.Lesson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hoangtuthinhthao.languru.views.activities.MainActivity.*;

public class LoadLesson {
    private static ApiLessonService apiInterface;
    private Context context;

    public LoadLesson(Context context) {
        this.context = context;
        apiInterface = ApiClient.getClient(this.context).create(ApiLessonService.class);
    }

    public void byTopic(final String topicName, final LoadLessonCallback callback) {
        Call<ArrayList<Lesson>>call = apiInterface.getByTopic(topicName);

        call.enqueue(new Callback<ArrayList<Lesson>>() {
            @Override
            public void onResponse(Call<ArrayList<Lesson>> call, Response<ArrayList<Lesson>> response) {
                if(response.isSuccessful()){
                    ArrayList<Lesson> lessonList = response.body(); // have your all data
                    Log.i("loaded", String.valueOf(lessonList.size()));

                    callback.successLoadLesson(topicName,lessonList);

                } else {

                    callback.failLoadLesson(response.errorBody().toString());

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Lesson>> call, Throwable t) {
                Log.e("SignupFragment", t.getMessage());
                callback.failLoadLesson(t.getMessage());
            }

        });
    }
}

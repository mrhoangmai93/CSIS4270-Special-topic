package com.hoangtuthinhthao.languru.controllers.loadServices.topic;

import android.content.Context;
import android.util.Log;

import com.hoangtuthinhthao.languru.controllers.api.ApiClient;
import com.hoangtuthinhthao.languru.controllers.api.ApiLessonService;
import com.hoangtuthinhthao.languru.models.responses.Topic;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadTopic {
    private static ApiLessonService apiInterface;
    private Context context;
    private static  LoadTopic instance;

    public LoadTopic(Context context) {
        this.context = context;
        apiInterface = ApiClient.getClient(this.context).create(ApiLessonService.class);
    }
    public static synchronized LoadTopic getInstance(Context context) {
        if (instance == null)
            instance = new LoadTopic(context);

        return instance;
    }
    public void byTopic(final String topicName, final LoadTopicCallback callback) {
        Call<Topic>call = apiInterface.getByTopic(topicName);

        call.enqueue(new Callback<Topic>() {
            @Override
            public void onResponse(Call<Topic> call, Response<Topic> response) {
                if(response.isSuccessful()){
                    Topic topic = response.body();

                    callback.successLoadLesson(topicName,topic);

                } else {

                    callback.failLoadLesson(response.errorBody().toString());

                }
            }

            @Override
            public void onFailure(Call<Topic> call, Throwable t) {
                Log.e("SignupFragment", t.getMessage());
                callback.failLoadLesson(t.getMessage());
            }

        });
    }
}

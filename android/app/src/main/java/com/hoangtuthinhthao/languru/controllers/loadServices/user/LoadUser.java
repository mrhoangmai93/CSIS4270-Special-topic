package com.hoangtuthinhthao.languru.controllers.loadServices.user;

import android.content.Context;
import android.util.Log;

import com.hoangtuthinhthao.languru.controllers.api.ApiAuthService;
import com.hoangtuthinhthao.languru.controllers.api.ApiClient;
import com.hoangtuthinhthao.languru.controllers.api.ApiLessonService;
import com.hoangtuthinhthao.languru.controllers.loadServices.topic.LoadTopic;
import com.hoangtuthinhthao.languru.controllers.loadServices.topic.LoadTopicCallback;
import com.hoangtuthinhthao.languru.models.responses.Progress;
import com.hoangtuthinhthao.languru.models.responses.Topic;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadUser {
    private static ApiAuthService apiInterface;
    private Context context;
    private static LoadUser instance;

    public LoadUser(Context context) {
        this.context = context;
        apiInterface = ApiClient.getClient(this.context).create(ApiAuthService.class);
    }
    public static synchronized LoadUser getInstance(Context context) {
        if (instance == null)
            instance = new LoadUser(context);

        return instance;
    }

    public void getProgress(final LoadUserData callback) {
        Call<ArrayList<Progress>> call = apiInterface.getProgress();

        call.enqueue(new Callback<ArrayList<Progress>>() {
            @Override
            public void onResponse(Call<ArrayList<Progress>> call, Response<ArrayList<Progress>> response) {
                if(response.isSuccessful()){
                    ArrayList<Progress> topics = response.body();

                    callback.onLoadProgressSucess(topics);

                } else {

                    callback.loadFail(response.errorBody().toString());

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Progress>> call, Throwable t) {
                Log.e("SignupFragment", t.getMessage());
                callback.loadFail(t.getMessage());
            }

        });
    }

    public interface LoadUserData {
        void onLoadProgressSucess(ArrayList<Progress> progressArrayList);
        void loadFail(String msg);
    }
}

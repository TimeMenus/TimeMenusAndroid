package com.timemenus.android.services;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers({
            "Content-Type: application/json",
            "Authorization: key=AAAAPG3xkOk:APA91bFq4ZmwqeK6JRK4_haP8XcOeR7FxuS_Qphd3vyUL-" +
                    "njZEeEtd5G_OCM-j_MkmX0bU09EYjzqsOvCl60g5rpU0jIelugFSB2VlYN7BUK7L1eAkUh5x70brk2_" +
                    "JQBq8wINPwcvUTji9GzfM8eAIQZ7MuWcsgDiQ"
    })
    @POST("fcm/send")
    Call<JsonObject> postCall(@Body JsonObject body);
}

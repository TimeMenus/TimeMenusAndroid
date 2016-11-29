package com.timemenus.android.services;

        import com.google.gson.JsonObject;

        import org.json.JSONObject;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;
        import retrofit2.Retrofit;
        import retrofit2.converter.gson.GsonConverterFactory;

public class FCMHelper {

    private static final String URL_SEND = "https://fcm.googleapis.com/";

    public static void sendNotification(JsonObject obj) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_SEND)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService request = retrofit.create(APIService.class);

        Call<JsonObject> call = request.postCall(obj);

        System.out.println("success message " + call.request().headers());

        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                System.out.println("success");
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable throwable) {
                System.out.println("failure");

            }
        });

    }


}
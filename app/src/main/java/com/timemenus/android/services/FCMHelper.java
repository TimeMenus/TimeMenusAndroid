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

    public static String sendNotification(JsonObject obj) {

        final String[] result = {new String()};

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_SEND)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService request = retrofit.create(APIService.class);
        Call<JsonObject> call = request.postCall(obj);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                result[0] = "success";
                System.out.println("successfull");
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable throwable) {
                result[0] = "failure";
                System.out.println("failure");

            }
        });

        return result[0];
    }


}
package com.acemoney.matm.model.api;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {


     public static String BASE_URL = "https://aceneobank.com/";
     //    public static String BASE_URL = "https://appbackend.acemoney.in/";
   // public static String BASE_URL = "https://aceneobank.stage.acepe.co.in/api/mercator/";

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        OkHttpClient client = getClient();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
//                .addNetworkInterceptor(encryptionInterceptor)
//                .addNetworkInterceptor(decryptionInterceptor)
                .addNetworkInterceptor(new AddHeaderInterceptor())
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .retryOnConnectionFailure(true)
//                .cache(new Cache(getCacheDir(), 10 * 1024 * 1024))
                .build();
        client.connectionPool().evictAll();
        return client;
    }


    public static class AddHeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            Response response=chain.proceed(builder.build());
            return response;
        }
    }

}

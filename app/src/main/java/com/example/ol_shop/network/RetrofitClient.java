package com.example.ol_shop.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BoseUrl = "http://192.168.0.102:3000/";

    //    private static final String BoseUrl = "http://172.20.10.10:3000/";
    public static ManagerService getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BoseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ManagerService.class);
    }
}

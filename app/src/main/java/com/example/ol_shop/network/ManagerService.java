package com.example.ol_shop.network;

import com.example.ol_shop.model.Account;
import com.example.ol_shop.model.Bill;
import com.example.ol_shop.model.Category;
import com.example.ol_shop.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ManagerService {
    @FormUrlEncoded
    @POST("api/login")
    Call<Account> login(@Field("username") String username, @Field("passwd") String passwd);
    @POST("api/createAccount")
    Call<Account.Data> register(@Body Account.Data account);
    @GET("api/account/{id}")
    Call<Account.Data> getAccountById(@Path("id") String id);
    @GET("api/listCategory")
    Call<List<Category>> getListCategory();
    @GET("api/listProduct")
    Call<List<Product>> getListProduct();
    @GET("api/productById/{id}")
    Call<Product> getProductById(@Path("id") String id);
    @POST("api/createBill")
    Call<Bill> createBill(@Body Bill bill);
    @GET("api/getListBillByIdAccount/{id}")
    Call<List<Bill>> getListBillByIdAccount(@Path("id") String id);
    @GET("api/productByIdCategory/{id}")
    Call<List<Product>> getListProductByIdCategory(@Path("id") String id);
}

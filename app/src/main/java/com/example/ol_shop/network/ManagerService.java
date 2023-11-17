package com.example.ol_shop.network;

import com.example.ol_shop.model.Account;
import com.example.ol_shop.model.Bill;
import com.example.ol_shop.model.Category;
import com.example.ol_shop.model.ChangePassword;
import com.example.ol_shop.model.Product;
import com.example.ol_shop.model.ProductPost;
import com.example.ol_shop.model.Statistical;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ManagerService {
    @FormUrlEncoded
    @POST("api/login")
    Call<Account> login(@Field("username") String username, @Field("passwd") String passwd);
    @POST("api/createAccount")
    Call<Account.Data> register(@Body Account.Data account);
    @POST("api/createBill")
    Call<Bill> createBill(@Body Bill bill);
    @POST("api/createCategory")
    Call<Category> postCategory(@Body Category category);
    @POST("api/createProduct")
    Call<ProductPost> postProduct(@Body ProductPost productPost);

    @GET("api/account/{id}")
    Call<Account.Data> getAccountById(@Path("id") String id);
    @GET("api/listCategory")
    Call<List<Category>> getListCategory();
    @GET("api/listProduct")
    Call<List<Product>> getListProduct();
    @GET("api/productById/{id}")
    Call<Product> getProductById(@Path("id") String id);
    @GET("api/getListBillByIdAccount/{id}")
    Call<List<Bill>> getListBillByIdAccount(@Path("id") String id);
    @GET("api/productByIdCategory/{id}")
    Call<List<Product>> getListProductByIdCategory(@Path("id") String id);
    @GET("api/listBill")
    Call<List<Bill>> getListBill();
    @GET("api/statisticalBill")
    Call<Statistical> getStatisticalBill();
    @GET("api/getCategoryById/{id}")
    Call<Category> getCategoryById(@Path("id") String id);
    @GET("api/productById2/{id}")
    Call<ProductPost> getProductById_2(@Path("id") String id);
    @GET("api/listAccount")
    Call<List<Account.Data>> getListAccount();

    @PUT("api/category/{id}")
    Call<Category> putCategory(@Path("id") String id,@Body Category category);
    @PUT("api/product/{id}")
    Call<ProductPost> putProduct(@Path("id") String id, @Body ProductPost productPost);
    @PUT("api/account/{id}")
    Call<Account.Data> putAccount(@Path("id") String id, @Body Account.Data account);
    @PUT("api/accountChangePasswd/{id}")
    Call<ChangePassword> putChangePassword(@Path("id") String id, @Body ChangePassword changePassword);

}

package com.laboratorio.myapplication.service;

import com.laboratorio.myapplication.model.BodyLoginRequest;
import com.laboratorio.myapplication.model.BodyRecoveryPasswordConfirm;
import com.laboratorio.myapplication.model.BodyRecoveryPasswordEmail;
import com.laboratorio.myapplication.model.CartBodyRequest;
import com.laboratorio.myapplication.model.CartHistory;
import com.laboratorio.myapplication.model.Category;
import com.laboratorio.myapplication.model.General;
import com.laboratorio.myapplication.model.LoginResponse;
import com.laboratorio.myapplication.model.Node;
import com.laboratorio.myapplication.model.Producer;
import com.laboratorio.myapplication.model.Product;
import com.laboratorio.myapplication.model.Report;
import com.laboratorio.myapplication.model.ReportPage;
import com.laboratorio.myapplication.model.User;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    @GET("/api/product")
    Call<List<Product>> getProducts();

    @GET("/api/category")
    Call<List<Category>> getCategories();

    @GET("/api/news")
    Call<ReportPage> getReports();

    @GET("/api/news/{id}")
    Call<Report> getReport(@Path("id") Long id);

    //TODO: Chequear
    @GET("/api/product/{id}")
    Call<Product> getProduct(@Path("id") Long id);

    @GET("/api/producer")
    Call<List<Producer>> getProducers();

    //TODO: Chequear
    @GET("/api/producer/{id}")
    Call<Producer> getProducer(@Path("id") Long id);

    @GET("/api/node")
    Call<List<Node>> getNodes();

    @POST("/api/token/generate-token")
    Call<LoginResponse> login(@Body BodyLoginRequest body);

    @PUT("/api/user/{id}")
    Call<User> updateProfile(@Header("Authorization") String token, @Body User body, @Path("id") Long id);

    @GET("/api/general/active")
    Call<General> getGeneralActive();

    @POST("/api/cart")
    Call<Object> saveCart(@Header("Authorization") String token, @Body CartBodyRequest body);

    @POST("api/user/signup")
    Call<Object> saveUser(@Body User body);

    @POST("api/email/recovery")
    Call<Object> getCodeRecoveryPassword(@Body BodyRecoveryPasswordEmail body);

    @POST("api/email/recovery/confirm")
    Call<Object> changePassword(@Body BodyRecoveryPasswordConfirm body);

    @GET("api/cart")
    public Call<List<CartHistory>> getCartHistory(@Header("Authorization") String token,@Query("properties")HashMap<Object,Object> firstParameter, @Query("range") String range, @Query("sort") String sort);
}

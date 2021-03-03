package com.laboratorio.myapplication.service;

import com.laboratorio.myapplication.model.BodyLoginRequest;
import com.laboratorio.myapplication.model.Category;
import com.laboratorio.myapplication.model.LoginResponse;
import com.laboratorio.myapplication.model.Node;
import com.laboratorio.myapplication.model.ReportPage;
import com.laboratorio.myapplication.model.Producer;
import com.laboratorio.myapplication.model.Product;
import com.laboratorio.myapplication.model.Report;
import com.laboratorio.myapplication.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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
}

package com.laboratorio.myapplication.service;

import com.laboratorio.myapplication.model.Category;
import com.laboratorio.myapplication.model.Node;
import com.laboratorio.myapplication.model.Producer;
import com.laboratorio.myapplication.model.Product;
import com.laboratorio.myapplication.model.Report;

import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {

    @GET("/api/product")
    Call<List<Product>> getProducts();

    @GET("/api/category")
    Call<List<Category>> getCategories();

    @GET("/api/news")
    Call<List<Report>> getReports();

    @GET("/api/news/{id}")
    Call<Report> getReport();

    //TODO: Chequear
    @GET("/api/product/{id}")
    Call<Product> getProduct();

    @GET("/api/producer")
    Call<List<Producer>> getProducers();

    //TODO: Chequear
    @GET("/api/producer/{id}")
    Call<Producer> getProducer();

    @GET("/api/node")
    Call<List<Node>> getNodes();
}

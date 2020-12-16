package com.laboratorio.myapplication.service;

import com.laboratorio.myapplication.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {

    @GET("/api/product")
    Call<List<Product>> getProducts();
}

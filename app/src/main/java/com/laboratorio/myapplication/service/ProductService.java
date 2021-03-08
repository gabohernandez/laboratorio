package com.laboratorio.myapplication.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laboratorio.myapplication.model.Product;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ProductService extends IntentService {

    private Service service;

    public ProductService() {
        super("ProductService");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://ec2-3-227-239-131.compute-1.amazonaws.com/api/product")
                .addConverterFactory(JacksonConverterFactory.create(mapper)).build();

        Service service = retrofit.create(Service.class);

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            List<Product> products = service.getProducts().execute().body();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


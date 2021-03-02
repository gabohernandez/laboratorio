package com.laboratorio.myapplication.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laboratorio.myapplication.model.Product;
import com.laboratorio.myapplication.model.Report;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ReportService extends IntentService {

    private Service service;

    public ReportService(){
        super("ReportService");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,true);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://ec2-3-227-239-131.compute-1.amazonaws.com/api/news")
                .addConverterFactory(JacksonConverterFactory.create(mapper)).build();

        Service service = retrofit.create(Service.class);

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            List<Report> report = service.getReports().execute().body();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}


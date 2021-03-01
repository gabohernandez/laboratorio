package com.laboratorio.myapplication.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laboratorio.myapplication.model.Node;


import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class NodeService extends IntentService  {

    private Service service;

    public NodeService(){
        super("NodeService");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://ec2-3-227-239-131.compute-1.amazonaws.com/api/node")
                .addConverterFactory(JacksonConverterFactory.create(mapper)).build();

        Service service = retrofit.create(Service.class);

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            List<Node> nodes = service.getNodes().execute().body();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

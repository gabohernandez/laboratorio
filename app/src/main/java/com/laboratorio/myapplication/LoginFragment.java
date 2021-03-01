package com.laboratorio.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laboratorio.myapplication.model.Report;

import java.util.List;

public class LoginFragment extends Fragment {



    public static LoginFragment newInstance(int columnCount) {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            return inflater.inflate(R.layout.login, container, false);
    }
}

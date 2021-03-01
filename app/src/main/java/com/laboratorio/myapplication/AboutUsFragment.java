package com.laboratorio.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;

public class AboutUsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.about_us, container, false);
    }

    public static AboutUsFragment newInstance(int columnCount) {
        AboutUsFragment fragment = new AboutUsFragment();
        return fragment;
    }

}

package com.laboratorio.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class RegisterFragment extends Fragment {

    private Context context;
    private Button addressButton;

    public RegisterFragment(){

    }

    public static RegisterFragment newInstance(int columnCount) {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register, container, false);
        this.context = container.getContext();

        addressButton =  view.findViewById(R.id.addressButton);
        addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).changeFragmentToAddress();
            }
        });

        return view;
    }
}

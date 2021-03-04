package com.laboratorio.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.laboratorio.myapplication.model.User;

public class RegisterFragment extends Fragment {

    private Context context;
    private Button addressButton;
    private User usuario;

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

        String name = view.findViewById(R.id.editTextTextPersonName).toString();
        String lastName = view.findViewById(R.id.editTextTextPersonName2).toString();
        String email = view.findViewById(R.id.editTextTextEmailAddress).toString();
        String age = view.findViewById(R.id.editTextAge).toString();
        String phone = view.findViewById(R.id.editTextPhone).toString();
        String password = view.findViewById(R.id.editTextTextPassword).toString();
        usuario = new User(name, lastName, email, phone, age, password);

        addressButton =  view.findViewById(R.id.addressButton);
        addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).changeFragmentToAddress(usuario);
            }
        });

        return view;
    }
}

package com.laboratorio.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

        addressButton =  view.findViewById(R.id.addressButton);
        addressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((TextView) view.findViewById(R.id.editTextTextPersonName)).getText().toString();
                String lastName = ((TextView)view.findViewById(R.id.editTextTextPersonName2)).getText().toString();
                String email = ((TextView)view.findViewById(R.id.editTextTextEmailAddress)).getText().toString();
                String age = ((TextView)view.findViewById(R.id.editTextAge)).getText().toString();
                String phone = ((TextView)view.findViewById(R.id.editTextPhone)).getText().toString();
                String password = ((TextView)view.findViewById(R.id.editTextTextPassword)).getText().toString();
                String confirmPassword = ((TextView)view.findViewById(R.id.editTextTextPassword2)).getText().toString();
                if (password.equals(confirmPassword)) {
                    usuario = new User(name, lastName, email, phone, age, password);
                    ((MainActivity) context).changeFragmentToAddress(usuario);
                } else {
                    ((MainActivity)context).showToast(false, "Las contraseñas ingresadas no coinciden", null);
                }
            }
        });

        return view;
    }
}

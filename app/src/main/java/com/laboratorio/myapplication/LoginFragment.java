package com.laboratorio.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.laboratorio.myapplication.model.Report;

import java.util.List;

public class LoginFragment extends Fragment {

    private Context context;
    public Button buttonDelete;
    public Button buttonRegister;

    public LoginFragment() {
    }

    public static LoginFragment newInstance(int columnCount) {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);
        this.context = container.getContext();
        buttonDelete = view.findViewById(R.id.loginbutton);
        buttonRegister = view.findViewById(R.id.registerButton);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView name = (TextView) view.findViewById(R.id.emailEntry);
                TextView password = (TextView) view.findViewById(R.id.passwordEntry);

                if (context instanceof MainActivity){
                    ((MainActivity) context).login(name.getText().toString(),password.getText().toString());
                }
            }
            });
        buttonRegister.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).changeFragmentToRegister();
            }
        });
            return view;
    }
}

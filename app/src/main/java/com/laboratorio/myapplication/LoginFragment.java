package com.laboratorio.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class LoginFragment extends Fragment {

    public Button buttonLogin;
    public Button buttonRegister;
    public Button buttonRecovery;
    private Context context;

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
        buttonLogin = view.findViewById(R.id.loginbutton);
        buttonRecovery = view.findViewById(R.id.forgotpasswordbutton);
        buttonRegister = view.findViewById(R.id.registerButton);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView name = (TextView) view.findViewById(R.id.emailEntry);
                TextView password = (TextView) view.findViewById(R.id.passwordEntry);

                if (context instanceof MainActivity) {
                    ((MainActivity) context).login(name.getText().toString(), password.getText().toString());
                }
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).changeFragmentToRegister();
            }
        });
        buttonRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).changeFragmentToRecovery();
            }
        });
        return view;
    }
}

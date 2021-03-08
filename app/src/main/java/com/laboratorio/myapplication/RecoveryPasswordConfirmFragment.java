package com.laboratorio.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.laboratorio.myapplication.model.BodyRecoveryPasswordConfirm;

public class RecoveryPasswordConfirmFragment extends Fragment {

    private Context context;
    public Button confirmCode;

    public RecoveryPasswordConfirmFragment(){

    }

    public static RecoveryPasswordConfirmFragment newInstance(int columnCount) {
        RecoveryPasswordConfirmFragment fragment = new RecoveryPasswordConfirmFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recovery_newpassword, container, false);
        this.context = container.getContext();

        confirmCode = view.findViewById(R.id.confirmPasswordButton);
        confirmCode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (context instanceof MainActivity){
                    String newPassword = ((TextView) view.findViewById(R.id.newPassword)).getText().toString();
                    String confirmPassword = ((TextView) view.findViewById(R.id.newPasswordValidategit )).getText().toString();
                    if (newPassword.equals(confirmPassword)) {
                        BodyRecoveryPasswordConfirm body = new BodyRecoveryPasswordConfirm();
                        body.setCode(((TextView) view.findViewById(R.id.codeEntry)).getText().toString());
                        body.setEmail(confirmPassword);
                        body.setNewPassword(newPassword);
                        ((MainActivity) context).changeFragmentToPasswordAndLogin(body);
                    } else{
                        ((MainActivity)context).showToast(false, "Las contraseñas ingresadas no coinciden", null);
                    }
                }
            }
        });
        return view;
    }

}

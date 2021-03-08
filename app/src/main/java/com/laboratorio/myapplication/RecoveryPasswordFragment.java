package com.laboratorio.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class RecoveryPasswordFragment extends Fragment {
    private Context context;

    public RecoveryPasswordFragment() {

    }

    public static RecoveryPasswordFragment newInstance(int columnCount) {
        RecoveryPasswordFragment fragment = new RecoveryPasswordFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recovery_getcode, container, false);
        this.context = container.getContext();
        Button confirmCode = view.findViewById(R.id.confirmEmail);
        confirmCode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (context instanceof MainActivity) {

                    ((MainActivity) context).sendCodeToEmail(((TextView) view.findViewById(R.id.emailRecovery)).getText().toString());
                }
            }
        });
        return view;
    }

}

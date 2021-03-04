package com.laboratorio.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AddressFragment extends Fragment {

    private Context context;
    private Button finishButton;

    public AddressFragment() {
    }

    public static AddressFragment newInstance(int columnCount) {
        AddressFragment fragment = new AddressFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.address, container, false);
        this.context = container.getContext();

/*        finishButton =  view.findViewById(R.id.finishRegisterButton);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        return view;
    }

}

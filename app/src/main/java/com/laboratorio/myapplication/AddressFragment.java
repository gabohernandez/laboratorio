package com.laboratorio.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.laboratorio.myapplication.model.Address;

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

        String calle = view.findViewById(R.id.editTextTextPersonName3).toString();
        String entreCalles = view.findViewById(R.id.editTextTextPersonName5).toString();
        String numero = view.findViewById(R.id.editTextTextPersonName4).toString();
        String piso = view.findViewById(R.id.editTextTextPersonName6).toString();
        String dpto = view.findViewById(R.id.editTextTextPersonName7).toString();
        String descript = view.findViewById(R.id.editTextTextPersonName8).toString();

        Address direccion = new Address(calle,entreCalles,numero,piso,dpto,descript);

        finishButton =  view.findViewById(R.id.finishRegisterButton);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).saveAddress(direccion);
                ((MainActivity) context).saveUser();
                ((MainActivity) context).changeFragmentToCategory();
            }
        });

        return view;
    }

}

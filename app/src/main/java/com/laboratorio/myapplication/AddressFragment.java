package com.laboratorio.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.laboratorio.myapplication.model.Address;
import com.laboratorio.myapplication.model.User;

public class AddressFragment extends Fragment {

    private Context context;
    private Button finishButton;
    public User usuario;

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



        finishButton =  view.findViewById(R.id.finishRegisterButton);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String calle = ((TextView) view.findViewById(R.id.editTextTextPersonName3)).getText().toString();
                String entreCalles = ((TextView) view.findViewById(R.id.editTextTextPersonName5)).getText().toString();
                String numero = ((TextView) view.findViewById(R.id.editTextTextPersonName4)).getText().toString();
                String piso = ((TextView) view.findViewById(R.id.editTextTextPersonName6)).getText().toString();
                String dpto = ((TextView) view.findViewById(R.id.editTextTextPersonName7)).getText().toString();
                String descript = ((TextView) view.findViewById(R.id.editTextTextPersonName8)).getText().toString();

                Address direccion = new Address(calle,entreCalles,numero,piso,dpto,descript);

                usuario.setAddress(direccion);
                ((MainActivity) context).saveUser(usuario);
            }
        });

        return view;
    }

}

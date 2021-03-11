package com.laboratorio.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.laboratorio.myapplication.model.General;
import com.laboratorio.myapplication.model.GeneralNode;
import com.laboratorio.myapplication.model.Node;

import java.math.BigDecimal;

public class CheckoutFragment extends Fragment {

    public General general;
    public BigDecimal total;
    public Node node;
    private Context context;

    public static CheckoutFragment newInstance() {

        Bundle args = new Bundle();

        CheckoutFragment fragment = new CheckoutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.checkout, container, false);

        view.findViewById(R.id.buyButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (node == null) {
                    ((MainActivity) context).showToast(true,"Se debe seleccionar un nodo",null);
                    return;
                }
                RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                String paymentMethod = radioButton.getText().toString();

                if (context instanceof MainActivity) {
                    ((MainActivity) context).buy(general, node, paymentMethod);
                }
            }
        });

        this.context = container.getContext();
        ((TextView) view.findViewById(R.id.amount)).setText(total.toString());

        if (node != null) {
            ((TextView) view.findViewById(R.id.selectedNode)).setText(node.getAddress().getStreet());

        }

        view.findViewById(R.id.showMapButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (context instanceof MainActivity) {
                    ((MainActivity) context).changeFragmentToMap();
                }
            }
        });

        return view;
    }

}

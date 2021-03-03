package com.laboratorio.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.laboratorio.myapplication.model.General;
import com.laboratorio.myapplication.model.GeneralNode;
import com.laboratorio.myapplication.model.Node;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class CheckoutFragment extends Fragment {

    private Context context;
    public General general;
    public BigDecimal total;

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
                Spinner spinner = (Spinner) view.findViewById(R.id.nodesSpinner);

                RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                String paymentMethod = radioButton.getText().toString();

                if (context instanceof MainActivity){
                    ((MainActivity) context).buy(general, ((GeneralNode) spinner.getSelectedItem()).getNode(), paymentMethod );
                }
            }
        });

        this.context = container.getContext();
        ((TextView) view.findViewById(R.id.amount)).setText(total.toString());

        Spinner spinner = (Spinner) view.findViewById(R.id.nodesSpinner);
        ArrayAdapter<GeneralNode> adapter = new ArrayAdapter(context,android.R.layout.simple_spinner_item,
                general.getActiveNodes());
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        /*
        NodeAdapter adapter = new NodeAdapter(this.context, new ArrayList(general.getActiveNodes().stream().map(GeneralNode::getNode).collect(Collectors.toList())));
        list.setAdapter(adapter);

        list.setOnItemClickListener( new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //((NodeAdapter)adapterView.getAdapter()).nodes.forEach();
                //view.setBackgroundColor(Color.RED) ;
            }
        }) ;

*/

        return view;
    }

}

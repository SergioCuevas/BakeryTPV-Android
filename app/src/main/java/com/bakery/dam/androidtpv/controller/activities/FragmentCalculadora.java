package com.bakery.dam.androidtpv.controller.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bakery.dam.androidtpv.R;

/**
 * Created by DAM on 31/3/17.
 */

public class FragmentCalculadora extends Fragment {

    View view;
    private Button btn1;private Button btn2;private Button btn3;
    private Button btn4;private Button btn5;private Button btn6;
    private Button btn7;private Button btn8;private Button btn9;private Button btn0;
    private Button btnmas;private Button btnmenos;private Button btncoma;
    private TextView tv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.calculate, container, false);
        btn1 = (Button) view.findViewById(R.id.button1);btn2 = (Button) view.findViewById(R.id.button2);btn3 = (Button) view.findViewById(R.id.button3);
        btn4 = (Button) view.findViewById(R.id.button4);btn5 = (Button) view.findViewById(R.id.button5);btn6 = (Button) view.findViewById(R.id.button6);
        btn7 = (Button) view.findViewById(R.id.button7);btn8 = (Button) view.findViewById(R.id.button8);btn9 = (Button) view.findViewById(R.id.button9);
        btn0 = (Button) view.findViewById(R.id.button0);btnmas = (Button) view.findViewById(R.id.mas);btnmenos = (Button) view.findViewById(R.id.menos);
        btncoma = (Button) view.findViewById(R.id.coma);
        tv= (TextView) view.findViewById(R.id.numeros);

        View.OnClickListener buttonListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(v.getId()!=R.id.mas&&v.getId()!=R.id.menos&&v.getId()!=R.id.coma&&v.getId()!=R.id.coma){
                    Button b = (Button) view.findViewById(v.getId());
                    String num = b.getText().toString()+"";
                    int numero = Integer.parseInt(num);
                    tv.setText(tv.getText().toString()+num);
                } else if(v.getId()==R.id.coma){
                    if(!tv.getText().toString().contains(".")) {
                        tv.setText(tv.getText().toString() + ".");
                    }
                }
            }
        };
        btn1.setOnClickListener(buttonListener);btn2.setOnClickListener(buttonListener);btn3.setOnClickListener(buttonListener);
        btn4.setOnClickListener(buttonListener);btn5.setOnClickListener(buttonListener);btn6.setOnClickListener(buttonListener);
        btn7.setOnClickListener(buttonListener);btn8.setOnClickListener(buttonListener);btn9.setOnClickListener(buttonListener);
        btn0.setOnClickListener(buttonListener);btnmas.setOnClickListener(buttonListener);btnmenos.setOnClickListener(buttonListener);
        btncoma.setOnClickListener(buttonListener);
        return view;
    }


}

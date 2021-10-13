package com.example.mydrobe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    int mCounter = 0;
    int mlt=1;
    TextView txv,coin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void recuperarP(View view){
        setContentView(R.layout.activity_main);
        txv = (TextView) findViewById (R.id.tx_puntos);
        txv.setText(Integer.toString(mCounter));
    }

    public void irTienda(View view) {
        setContentView(R.layout.interfaztienda);
        coin = (TextView) findViewById (R.id.coins);
        coin.setText(Integer.toString(mCounter));
    }

    public void cliker(View view) {
        mCounter=mCounter+(1*mlt);
        txv = (TextView) findViewById (R.id.tx_puntos);
        txv.setText(Integer.toString(mCounter));
    }
    public void multiplicador(View view){
        coin = (TextView) findViewById (R.id.coins);
        String c = (String) coin.getText();
        System.out.println(c);
        int num = Integer.parseInt(c);
        if (num<10*mlt){
            Snackbar mySnackbar = Snackbar.make(view, "No tiene dinero suficiente", 1000);
            mySnackbar.show();
        }
        else {
            mCounter=mCounter-10*mlt;
            mlt++;
            coin.setText(Integer.toString(mCounter));
        }
    }

}
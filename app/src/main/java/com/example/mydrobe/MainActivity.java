package com.example.mydrobe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int mCounter = 0;
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
        mCounter++;
        txv = (TextView) findViewById (R.id.tx_puntos);
        txv.setText(Integer.toString(mCounter));
    }

}
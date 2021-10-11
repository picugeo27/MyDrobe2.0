package com.example.mydrobe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int mCounter = 0;
    Button btn;
    TextView txv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById (R.id.bt);
        txv = (TextView) findViewById (R.id.tx);
    }
    public void sendMessage(View view) {
        setContentView(R.layout.interfaztienda);
    }
    public void cliker(View view) {
        mCounter++;
        txv.setText(Integer.toString(mCounter));
    }
    public void back(View view) {
        setContentView(R.layout.activity_main);
    }
}
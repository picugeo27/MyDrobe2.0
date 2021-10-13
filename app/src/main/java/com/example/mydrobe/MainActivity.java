package com.example.mydrobe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    int modo = 0;
    ArrayList<String> poolFrasesNormales = new ArrayList<>();
    ArrayList<String> poolFrasesObscenas = new ArrayList<>();
    Usuario usuario = new Usuario();

    int mCounter = 0;
    int mlt=1;
    TextView txv,coin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void cliker(View view) {
        mCounter=mCounter+(1*mlt);
        txv = (TextView) findViewById (R.id.tx_puntos);
        txv.setText(Integer.toString(mCounter));
    }
    public void multiplicador(View view){
        coin = (TextView) findViewById (R.id.tx_puntos_tienda);
        String c = (String) coin.getText();
        int num = Integer.parseInt(c);
        if (num<10*mlt){
            Snackbar mySnackbar = Snackbar.make(view, "No tienes dinero suficiente", 1000);
            mySnackbar.show();
        }
        else {
            mCounter=mCounter-10*mlt;
            mlt++;
            coin.setText(Integer.toString(mCounter));
        }
    }
    /*
    ***********************************
    *
    * Setters y getters
    *
    * *********************************
    */
    public int getModo() {
        return modo;
    }

    public void setModo(int modo) {
        this.modo = modo;
    }

    public ArrayList<String> getPoolFrasesNormales() {
        return poolFrasesNormales;
    }

    public ArrayList<String> getPoolFrasesObscenas() {
        return poolFrasesObscenas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /*
    ********************************
    *
    * Botones que cambian de interfaz (y el modo)
    *
    * *******************************
    */
    public void showTienda(View view) {
        setContentView(R.layout.interfaztienda);
    }

    public void showObsceno (View view){
        modo=1;
        setContentView(R.layout.interfazobscene);
    }

    public void showMenu (View view){
        modo=0;
        setContentView(R.layout.activity_main);
    }

    public void atras (View view){
        if (modo==0) {
            setContentView(R.layout.activity_main);
        } else
            setContentView(R.layout.interfazobscene);
    }
}
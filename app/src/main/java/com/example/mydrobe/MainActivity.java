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

    int mlt=1;
    TextView txPuntos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txPuntos = (TextView) findViewById (R.id.tx_puntos);
    }

    public void cliker(View view) {
        usuario.clicar();
        txPuntos.setText(Integer.toString(usuario.getContador()));
    }

    public void multiplicador(View view){
        String c = (String) txPuntos.getText();
        int num = Integer.parseInt(c);
        if (num<10*mlt){
            Snackbar mySnackbar = Snackbar.make(view, "No tienes dinero suficiente", 1000);
            mySnackbar.show();
        }
        else {
            mlt++;
            txPuntos.setText(Integer.toString(usuario.getContador()));
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
        txPuntos = (TextView) findViewById(R.id.tx_puntos_tienda);
        txPuntos.setText(Integer.toString(usuario.getContador()));

    }

    public void showObsceno (View view){
        modo=1;
        setContentView(R.layout.interfazobscene);
        txPuntos = (TextView) findViewById(R.id.tx_puntos_obsceno);
        txPuntos.setText(Integer.toString(usuario.getContador()));
    }

    public void showMenu (View view){
        modo=0;
        setContentView(R.layout.activity_main);
        txPuntos = (TextView) findViewById (R.id.tx_puntos);
        txPuntos.setText(Integer.toString(usuario.getContador()));
    }

    public void atras (View view){
        if (modo==0) {
            showMenu(view);
        } else
            showObsceno(view);
    }
}
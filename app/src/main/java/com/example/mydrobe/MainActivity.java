package com.example.mydrobe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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
        poolFrasesNormales.add("pepe");
        poolFrasesObscenas.add("pepe obsceno");
    }

    public void cliker(View view) {
        usuario.clicar();
        txPuntos.setText(Integer.toString(usuario.getContador()));
        if (modo==0) {
            FraseAleatoria(usuario.getPoolfrasesNormales());
        } else{
            FraseAleatoria(usuario.getPoolfrasesObscenas());
        }
    }

    public void FraseAleatoria(@NonNull ArrayList<String> poolFrases) {
        int RangoAleatorio = poolFrases.size();
        Random claseRandom = new Random(); // Esto crea una instancia de la Clase Random
        claseRandom.nextInt(RangoAleatorio);
        String FraseMostrar = poolFrases.get(claseRandom.nextInt(RangoAleatorio));
        TextView fraseAleatoria;
        fraseAleatoria = (TextView) findViewById (R.id.tx_frases_bonitas);
        fraseAleatoria.setText(FraseMostrar);
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
        txPuntos = (TextView) findViewById(R.id.tx_puntos);
        txPuntos.setText(Integer.toString(usuario.getContador()));
    }

    public void showMenu (View view){
        modo=0;
        setContentView(R.layout.activity_main);
        txPuntos = (TextView) findViewById (R.id.tx_puntos);
        txPuntos.setText(Integer.toString(usuario.getContador()));
    }

    public void showCrearFrase (View view){
        setContentView(R.layout.frases_custom);
    }

    public void atras (View view){
        if (modo==0) {
            showMenu(view);
        } else
            showObsceno(view);
    }

    /*
     ********************************
     *
     * Botones de tienda
     *
     * *******************************
     */

    public void MejorarClicks(View view){
        if(usuario.pago(usuario.getValorClick()*10)){
            usuario.aplicarMejoraClicks();
            txPuntos.setText(Integer.toString(usuario.getContador()));
        } else {
            Snackbar mySnackbar = Snackbar.make(view, "No tienes dinero suficiente", 1000);
            mySnackbar.show();
        }
    }
    public void CrearFrase(View view){
        EditText eText = (EditText) findViewById(R.id.frasesCreadas);
        String str = eText.getText().toString();
        if (usuario.pago(50)){
            if (modo==0) {
                usuario.AnadirFrase(usuario.getPoolfrasesNormales(), str);
            } else{
                usuario.AnadirFrase(usuario.getPoolfrasesObscenas(), str);
            }
            showTienda(view);
        } else{
            Snackbar mySnackbar = Snackbar.make(view, "No tienes dinero suficiente", 1000);
            mySnackbar.show();
        }
    }

    public void ComprarFrase(View view){
        String frase;
        if (usuario.pago(25)){
            if (modo==0){
            frase = usuario.yaEstaFrase(poolFrasesNormales,usuario.getPoolfrasesNormales());
            usuario.AnadirFrase(usuario.getPoolfrasesNormales(),frase);
        } else {
            frase = usuario.yaEstaFrase(poolFrasesObscenas,usuario.getPoolfrasesObscenas());
            usuario.AnadirFrase(usuario.getPoolfrasesObscenas(),frase);
        } if (frase==null) {
                Snackbar mySnackbar = Snackbar.make(view, "Ya has desbloqueado todas las frases", 1000);
                mySnackbar.show();
            }
    }
    }
}

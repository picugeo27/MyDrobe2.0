package com.example.mydrobe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
        try {
            anadirFrases("normal", poolFrasesNormales);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cliker(View view) {
        usuario.clicar();
        txPuntos.setText(Integer.toString(usuario.getContador()));

        int cont = usuario.getContador();
        if ((cont%10)==0){
            FraseAleatoria(poolFrasesNormales);
        }
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



    public void FraseAleatoria(@NonNull ArrayList<String> poolFrases) {
        int RangoAleatorio = poolFrases.size();
        int numeroAleatorio = (int) (Math.random() * RangoAleatorio);
        String FraseMostrar = poolFrases.get(numeroAleatorio);


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


    public static void anadirFrases(String TipoFrase, ArrayList<String> poolFrases) throws FileNotFoundException, IOException {
        String direccionArchivo="";
        if (TipoFrase =="normal"){
            direccionArchivo = "FrasesNormales.txt" ;
        }else if(TipoFrase=="obsceno"){
            direccionArchivo = "FrasesObscenas.txt" ;
        }
        String cadena;
        FileReader f = new FileReader(direccionArchivo);
        BufferedReader b = new BufferedReader(f);
        while ((cadena = b.readLine()) != null) {
            poolFrases.add(cadena);
        }
        b.close();
    }

    public boolean MejorarClicks(int coste){
        if(usuario.pago(coste)){
            usuario.aplicarMejoraClicks();
            return true;
        }
        return false;
    }


}
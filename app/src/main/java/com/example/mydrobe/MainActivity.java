package com.example.mydrobe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Context context = this;
    File fichero = new File("/data/user/0/com.example.mydrobe/files/usuarioUnico.bat");
    int modo = 0;
    ArrayList<String> poolFrasesNormales = new ArrayList<>();
    ArrayList<String> poolFrasesObscenas = new ArrayList<>();
    Usuario usuario = new Usuario();

    int requestCode = 200;
    int mlt=1;
    TextView txPuntos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verificarPermisos();

        txPuntos = (TextView) findViewById (R.id.tx_puntos);
            try {
                inicializarSistema();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }


    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            guardarUsuario();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void verificarPermisos() {
        int permES = ContextCompat.checkSelfPermission( context , Manifest.permission.READ_EXTERNAL_STORAGE);
        int permEW = ContextCompat.checkSelfPermission( context , Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (PackageManager.PERMISSION_GRANTED == permES && PackageManager.PERMISSION_GRANTED == permEW){
            Toast.makeText( context, "Permiso garanted", Toast.LENGTH_SHORT).show();
        }else{
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},this.requestCode);
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("¿Deseas salis de MYDrove?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent=new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            try {
                                guardarUsuario();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void guardarUsuario() throws IOException {
        ObjectOutputStream oos = null;
        oos = new ObjectOutputStream(new FileOutputStream(fichero));
        oos.writeObject(usuario);
        oos.close();
    }

    private void inicializarSistema() throws IOException, ClassNotFoundException { //Cargamos el ususario y las frases en el MainActivity..
        //Cargamos el usuario
        long files = fichero.length();
        boolean x = files == 0;
        if(!x){
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichero));
            this.usuario = (Usuario) ois.readObject();
            ois.close();
        }
        //Cargamos las frases en los arrays correspondientes.
        String linea = null;
        BufferedReader br = new BufferedReader(new FileReader("FrasesNormales.txt"));
        while((linea = br.readLine()) != null) {
            this.poolFrasesNormales.add(linea);

            Toast.makeText( context, linea, Toast.LENGTH_SHORT).show();
        }
        br.close();

    }

    public void cliker(View view) {
        usuario.clicar();
        txPuntos.setText(Integer.toString(usuario.getContador()));
        if (modo==0) {
            fraseAleatoria(usuario.getPoolfrasesNormales());
        } else{
            fraseAleatoria(usuario.getPoolfrasesObscenas());
        }
    }

    public void fraseAleatoria(@NonNull ArrayList<String> poolFrases) {
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
package com.example.mydrobe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int PUNTOS_PARA_PRESTIGIO = 10000000;
    private static final int elegirFichero = 10;

    int modo = 0;
    ArrayList<String> poolFrasesNormales;
    ArrayList<String> poolFrasesObscenas;
    File fichero = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "usuario.bat");
    Usuario usuario = new Usuario();

    TextView txPuntos;
    MediaPlayer mpNormal, mpObsceno;
    Drawable skin = null;

    int skinActual = 0 ;
    Button buttonMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            inicializarSistema();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);
        txPuntos = (TextView) findViewById (R.id.tx_puntos);
        txPuntos.setText(Integer.toString(usuario.getContador()));
        frasesPredeterminadas();
        mpNormal = MediaPlayer.create(this, R.raw.audiobtnnormal);
        mpObsceno = MediaPlayer.create(this, R.raw.audiobtnobsceno);
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

    private boolean checkPermissions(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);
            return false;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("??Deseas salis de MYDrove?")
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
        else if (!fichero.exists()){
            if (checkPermissions()) {
                FileOutputStream fileOutputStream = null;
                fichero.createNewFile();
            }
        }

    }

    public void cliker(View view) {
        usuario.clicar();
        txPuntos.setText(Integer.toString(usuario.getContador()));
        if (modo==0) {
            fraseAleatoria(usuario.getPoolfrasesNormales());
            mpNormal.start();
        } else{
            fraseAleatoria(usuario.getPoolfrasesObscenas());
            mpObsceno.start();
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

    public void setPoolFrasesNormales(ArrayList<String> poolFrasesNormales) {
        this.poolFrasesNormales = poolFrasesNormales;
    }

    public void setPoolFrasesObscenas(ArrayList<String> poolFrasesObscenas) {
        this.poolFrasesObscenas = poolFrasesObscenas;
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
    * metodos que cambian la interfaz
    *
    * *******************************
    */

    //Cambia la interfaz a la tienda
    public void showTienda(View view) {
        setContentView(R.layout.interfaztienda);
        txPuntos = (TextView) findViewById(R.id.tx_puntos_tienda);
        txPuntos.setText(Integer.toString(usuario.getContador()));
    }

    //Cambia la interfaz a la tienda de skins
    public void showTiendaSkins(View view) {
        setContentView(R.layout.interfaztiendaskins);
        /*txPuntos = (TextView) findViewById(R.id.tx_puntos_tienda);
        txPuntos.setText(Integer.toString(usuario.getContador()));*/

    }

    //Cambia la interfaz al menu obsceno
    public void showObsceno (View view) {
        modo=1;
        setContentView(R.layout.interfazobscene);
        txPuntos = (TextView) findViewById(R.id.tx_puntos);
        txPuntos.setText(Integer.toString(usuario.getContador()));
    }

    //Cambia la interfaz al menu normal
    public void showMenu (View view){
        modo=0;
        setContentView(R.layout.activity_main);
        txPuntos = (TextView) findViewById (R.id.tx_puntos);
        txPuntos.setText(Integer.toString(usuario.getContador()));
        setSkin(view);
    }

    //Cambia la interfaz a el formulario para crear frases propias
    public void showCrearFrase (View view) {
        setContentView(R.layout.frases_custom);
    }

    //Vuelve a la interfaz anterior a tienda
    public void atras (View view){
        if (modo==0) {
            showMenu(view);

        } else
            showObsceno(view);
    }

    //Vuelve a la interfaz anterior a tienda skins
    public void atras2 (View view){
        showTienda(view);

    }


    /*
     ********************************
     *
     * metodos que cambian la skin
     *
     * *******************************
     */

    //Establece la skin que el usuario tenga selecionada
    public void setSkin(View view){
        buttonMain = findViewById(R.id.bt_moneda);

        switch (skinActual){
            case 0:
                buttonMain.setForeground(getDrawable(R.drawable.efecto_btn_moneda));
                break;

            case 1:
                    buttonMain.setForeground(getDrawable(R.drawable.skin_castana));
                    usuario.getSkinsCompradas().add("Casta??a");
             break;

            case 2:
                    buttonMain.setForeground(getDrawable(R.drawable.skin_pikachu));
                    usuario.getSkinsCompradas().add("Pikachu");

                break;
            case 3:

                    buttonMain.setForeground(getDrawable(R.drawable.skin_steve));
                    usuario.getSkinsCompradas().add("Steve");
                    break;
            case 4:
                    buttonMain.setForeground(getDrawable(R.drawable.skin_shrek));
                    usuario.getSkinsCompradas().add("Shrek");
                break;
            case 5:
                buttonMain.setForeground(skin);
                break;
        }
    }

    //Cliker
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            pressed(view);
        }
    };

    //Permite al usuario comprar skins si tiene los puntos necesarios
    public void pressed(View view){
        switch(view.getId()) {
            case R.id.btn_defecto:
                skinActual = 0;
                break;
            case R.id.btn_skin_1:
                if( usuario.getSkinsCompradas().contains("Casta??a") ) {
                    skinActual = 1;
                }
                else if (usuario.pago(500)) {
                    skinActual = 1;
                }
                break;
            case R.id.btn_skin_2:
                if( usuario.getSkinsCompradas().contains("Pikachu") ) {
                    skinActual = 2;
                }
                else if (usuario.pago(500)) {
                    skinActual = 2;
                }
                break;
            case R.id.btn_skin_3:
                if( usuario.getSkinsCompradas().contains("Steve") ) {
                    skinActual = 3;
                }
                else  if (usuario.pago(500)) {
                    skinActual = 3;
                }
                break;
            case R.id.btn_skin_4:
                if( usuario.getSkinsCompradas().contains("Shrek") ) {
                    skinActual = 4;
                }
                else  if (usuario.pago(500)) {
                    skinActual = 4;
                }
                break;
            case R.id.btn_galeria:
                skinActual = 5;
                cargarImagen();
                if(skin == null){
                    skinActual = 0;
                }
                break;
        }
    }

    //Permite cargar una imagen para ser utilizada como skin
    private void cargarImagen(){
        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(Intent.createChooser(intent,"Seleccione la galer??a"),elegirFichero);
    }

    //Transforma la imagen al tipo de fichero compatible con skin
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            try {
                Uri ruta = data.getData();
                InputStream imagen = getContentResolver().openInputStream(ruta);
                Bitmap vista = BitmapFactory.decodeStream(imagen);
                skin = new BitmapDrawable(getResources(), vista);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else{
            skin = null;
        }
    }
    /*
     ********************************
     *
     * Otros metodos
     *
     * *******************************
     */

    //Permite al usuario auimentar el numero de puntos obtenidos al hacer click a cambio de una cantidad de puntos
    public void mejorarClicks(View view){
        if(usuario.pago(usuario.getValorClick()*10)){
            usuario.aplicarMejoraClicks();
            txPuntos.setText(Integer.toString(usuario.getContador()));
        } else {
            Snackbar mySnackbar = Snackbar.make(view, "No tienes dinero suficiente", 1000);
            mySnackbar.show();
        }
    }
    //Permite al usuario crear una frase propia para ser a??adida a su pool de frases a cambio de una cantidad de puntos
    public void crearFrase(View view){
        EditText eText = (EditText) findViewById(R.id.frasesCreadas);
        String str = eText.getText().toString();
        if (usuario.pago(50)){
            if (modo==0) {
                usuario.anadirFrase(usuario.getPoolfrasesNormales(), str);
            } else{
                usuario.anadirFrase(usuario.getPoolfrasesObscenas(), str);
            }
            showTienda(view);
        } else{
            Snackbar mySnackbar = Snackbar.make(view, "No tienes dinero suficiente", 1000);
            mySnackbar.show();
        }
    }

    //Permite al usuario agregar una frase aleatoria a su pool de frases a cambio de una cantidad de puntos
    public void comprarFrase(View view){
        String frase;
        if (usuario.pago(25)){
            if (modo==0){
                frase = usuario.yaEstaFrase(poolFrasesNormales,usuario.getPoolfrasesNormales());
                usuario.anadirFrase(usuario.getPoolfrasesNormales(),frase);
            } else {
                frase = usuario.yaEstaFrase(poolFrasesObscenas,usuario.getPoolfrasesObscenas());
                usuario.anadirFrase(usuario.getPoolfrasesObscenas(),frase);
             } if (frase==null) {
                Snackbar mySnackbar = Snackbar.make(view, "Ya has desbloqueado todas las frases", 1000);
                mySnackbar.show();
                usuario.setContador(usuario.getContador()+30);
            }
        }
    }
    //Establece las frases iniciales.
    public void frasesPredeterminadas(){
        String a="";
        ArrayList<String> n=usuario.getPoolfrasesNormales();
        n.add(a);
        ArrayList<String> o=usuario.getPoolfrasesObscenas();
        o.add(a);
        ArrayList<String> normales = new ArrayList<String>(
                Arrays.asList("El ??nico modo de hacer un gran trabajo es amar lo que haces","Cuanto m??s duramente trabajo, m??s suerte tengo",
                        "La l??gica te llevar?? de la a a la z. la imaginaci??n te llevar?? a cualquier lugar","A veces la adversidad es lo que necesitas encarar para ser exitoso"));
        setPoolFrasesNormales(normales);
        ArrayList<String> obscenas = new ArrayList<String>(
                Arrays.asList("El metodo cascada es el mejor","ETA es una gran naci??n","Lo que nosotros hemos hecho, cosa que no hizo usted, es enga??ar a la gente",
                        "T?? y yo tenemos una cita y tu ropa no est?? invitada.","Tienes cara de ser el 9 que le falta a mi 6."));
        setPoolFrasesObscenas(obscenas);
    }

    //Permite al usuario reiniciar su progresso a cambio de obtener m??s puntos al hacer click permanentemente
    public void modoPrestigio(View view){
        if(usuario.getContador() > PUNTOS_PARA_PRESTIGIO){
            usuario.setModoPrestigio();
        }else{
            Toast toast = Toast.makeText(this, "Consigue " + PUNTOS_PARA_PRESTIGIO + " puntos para desbloquear esta opci??n", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /*
     ********************************
     *
     * Metodos de ayuda al usuario
     *
     * *******************************
     */

    //Muestra al ususario una ayuda textual unica en cada interfaz.
    public void ayuda(View view){
        TextView ab = findViewById(R.id.ayudaBoton);
        TextView af = findViewById(R.id.ayudaFrases);
        TextView ap = findViewById(R.id.ayudaPuntuacion);
        TextView at = findViewById(R.id.ayudaTienda);
        TextView ao = findViewById(R.id.modoObsceno);
        TextView ae = findViewById(R.id.ayudaEsc);
        if(ab.getVisibility() == View.VISIBLE){ //si es Visible lo pones Gone
            ab.setVisibility(View.GONE);
            af.setVisibility(View.GONE);
            ap.setVisibility(View.GONE);
            at.setVisibility(View.GONE);
            ao.setVisibility(View.GONE);
        }else{ // si no es Visible, lo pones
            ab.setVisibility(View.VISIBLE);
            af.setVisibility(View.VISIBLE);
            ap.setVisibility(View.VISIBLE);
            at.setVisibility(View.VISIBLE);
            ao.setVisibility(View.VISIBLE);
        }
    }


    public void ACF(View view){
        TextView ae = findViewById(R.id.ayudaEsc);
        TextView ac = findViewById(R.id.ayudaCrear);
        if(ae.getVisibility() == View.VISIBLE){ //si es Visible lo pones Gone
            ae.setVisibility(View.GONE);
            ac.setVisibility(View.GONE);
        }else{ // si no es Visible, lo pones
            ae.setVisibility(View.VISIBLE);
            ac.setVisibility(View.VISIBLE);
        }
    }
    public void ACTS(View view){
        TextView ae = findViewById(R.id.defecto);
        TextView ac = findViewById(R.id.elegir);
        if(ae.getVisibility() == View.VISIBLE){ //si es Visible lo pones Gone
            ae.setVisibility(View.GONE);
            ac.setVisibility(View.GONE);
        }else{ // si no es Visible, lo pones
            ae.setVisibility(View.VISIBLE);
            ac.setVisibility(View.VISIBLE);
        }
    }
    public void ACT(View view){
        TextView ae = findViewById(R.id.generador);
        TextView ac = findViewById(R.id.creador);
        TextView af = findViewById(R.id.skins);
        TextView ad = findViewById(R.id.mb);
        TextView ag = findViewById(R.id.ayudaPrestigio);
        if(ae.getVisibility() == View.VISIBLE){ //si es Visible lo pones Gone
            ae.setVisibility(View.GONE);
            ac.setVisibility(View.GONE);
            af.setVisibility(View.GONE);
            ad.setVisibility(View.GONE);
            ag.setVisibility(View.GONE);
        }else{ // si no es Visible, lo pones
            ae.setVisibility(View.VISIBLE);
            ac.setVisibility(View.VISIBLE);
            af.setVisibility(View.VISIBLE);
            ad.setVisibility(View.VISIBLE);
            ag.setVisibility(View.VISIBLE);
        }
    }


    /*
     ********************************
     */



}
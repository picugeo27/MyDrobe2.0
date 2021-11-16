package com.example.mydrobe;
import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.lang.Math;

public class Usuario  implements Serializable {
private ArrayList<String> poolfrasesNormales=new ArrayList<>();
private ArrayList<String> poolfrasesObscenas=new ArrayList<>();
private int valorClick= 1;
private int contador =  0;

    public ArrayList<String> getPoolfrasesNormales() {
        return poolfrasesNormales;
    }

    public void setPoolfrasesNormales(ArrayList<String> poolfrasesNormales) {
        this.poolfrasesNormales = poolfrasesNormales;
    }

    public ArrayList<String> getPoolfrasesObscenas() {
        return poolfrasesObscenas;
    }

    public void setPoolfrasesObscenas(ArrayList<String> poolfrasesObscenas) {
        this.poolfrasesObscenas = poolfrasesObscenas;
    }

    public int getValorClick() {
        return valorClick;
    }

    public void setValorClick(int valorClick) {
        this.valorClick = valorClick;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public int clicar () { // esta funcion añade al contador del usuario el valor de su click
            contador=contador+(valorClick);
            return contador;
    }


    public void aplicarMejoraClicks() {
        this.setValorClick((int) Math.round(valorClick*1.5));
    }

    public String yaEstaFrase(ArrayList<String> frases, ArrayList<String> frases2){
        boolean yaEsta;
        for (String delSistema: frases){
            yaEsta = frases2.contains(delSistema);
                if (!yaEsta){
                    return delSistema;
            }
        }
        return null;
    }

    public boolean pago(int coste){
        if (contador>=coste){
            contador= contador - coste;
            return true;
        }
        return false;
    }

    public void anadirFrase(@NonNull ArrayList<String> arrayFrases, String frase){//si quieres añadir a frases normales se le pasa el array normal, al igual que obsceno
        if (frase!=null) {
            arrayFrases.add(frase);
        }
    }
}



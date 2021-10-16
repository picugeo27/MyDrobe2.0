package com.example.mydrobe;

import java.io.Serializable;
import java.util.ArrayList;

public class Usuario  implements Serializable {
private ArrayList<String> poolfrasesNormales;
private ArrayList<String> poolfrasesObscenas;
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
        this.setValorClick(valorClick*2);
    }


    public boolean yaEstaFrase(ArrayList<String> frases, ArrayList<String> frases2){
        boolean yaEsta = false;
        for (String delUsuario: frases){
            for (String delSistema : frases2){
                yaEsta = delUsuario.equals(delSistema);
                if (yaEsta){
                    return yaEsta;
                }
            }
        }
        return yaEsta;
    }
}



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.io.Serializable;

/**
 *
 * @author beani
 */
public class Preferencias implements Serializable{
    String relacion;
    int deporte, politica, arte;
    String hijos;
    String interes;

    public Preferencias(String relacion, int deporte, int politica, int arte, String hijos, String interes) {
        this.relacion = relacion;
        this.deporte = deporte;
        this.politica = politica;
        this.arte = arte;
        this.hijos = hijos;
        this.interes = interes;
    }

    public String getRelacion() {
        return relacion;
    }

    public void setRelacion(String relacion) {
        this.relacion = relacion;
    }

    public int getDeporte() {
        return deporte;
    }

    public void setDeporte(int deporte) {
        this.deporte = deporte;
    }

    public int getPolitica() {
        return politica;
    }

    public void setPolitica(int politica) {
        this.politica = politica;
    }

    public int getArte() {
        return arte;
    }

    public void setArte(int arte) {
        this.arte = arte;
    }

    public String getHijos() {
        return hijos;
    }

    public void setHijos(String hijos) {
        this.hijos = hijos;
    }

    public String getInteres() {
        return interes;
    }

    public void setInteres(String interes) {
        this.interes = interes;
    }
    
    
}

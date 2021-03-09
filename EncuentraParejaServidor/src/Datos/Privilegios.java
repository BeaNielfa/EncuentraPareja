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
public class Privilegios implements Serializable {
    private int activar;
    private int modificar;
    private int alta;
    private int baja;
    private int privilegios;

    public Privilegios(int activar, int modificar, int alta, int baja, int privilegios) {
        this.activar = activar;
        this.modificar = modificar;
        this.alta = alta;
        this.baja = baja;
        this.privilegios = privilegios;
    }

    public int getActivar() {
        return activar;
    }

    public void setActivar(int activar) {
        this.activar = activar;
    }

    public int getModificar() {
        return modificar;
    }

    public void setModificar(int modificar) {
        this.modificar = modificar;
    }

    public int getAlta() {
        return alta;
    }

    public void setAlta(int alta) {
        this.alta = alta;
    }

    public int getBaja() {
        return baja;
    }

    public void setBaja(int baja) {
        this.baja = baja;
    }

    public int getPrivilegios() {
        return privilegios;
    }

    public void setPrivilegios(int privilegios) {
        this.privilegios = privilegios;
    }
}

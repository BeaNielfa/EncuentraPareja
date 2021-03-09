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
public class Firma implements Serializable{
    byte[] firma;
    byte[] mensaje;

    public Firma(byte[] firma, byte[] mensaje) {
        this.firma = firma;
        this.mensaje = mensaje;
    }

    public byte[] getFirma() {
        return firma;
    }

    public void setFirma(byte[] firma) {
        this.firma = firma;
    }

    public byte[] getMensaje() {
        return mensaje;
    }

    public void setMensaje(byte[] mensaje) {
        this.mensaje = mensaje;
    }
    
    
}

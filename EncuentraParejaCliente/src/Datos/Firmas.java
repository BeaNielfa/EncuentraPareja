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
public class Firmas implements Serializable{
    
    Firma firmaN;
    Firma firmaA;
    Firma firmaE;
    Firma firmaC;

    public Firmas(Firma firmaN, Firma firmaA, Firma firmaE, Firma firmaC) {
        this.firmaN = firmaN;
        this.firmaA = firmaA;
        this.firmaE = firmaE;
        this.firmaC = firmaC;
    }

    public Firma getFirmaN() {
        return firmaN;
    }

    public void setFirmaN(Firma firmaN) {
        this.firmaN = firmaN;
    }

    public Firma getFirmaA() {
        return firmaA;
    }

    public void setFirmaA(Firma firmaA) {
        this.firmaA = firmaA;
    }

    public Firma getFirmaE() {
        return firmaE;
    }

    public void setFirmaE(Firma firmaE) {
        this.firmaE = firmaE;
    }

    public Firma getFirmaC() {
        return firmaC;
    }

    public void setFirmaC(Firma firmaC) {
        this.firmaC = firmaC;
    }
    
    
    
    
    
    
}

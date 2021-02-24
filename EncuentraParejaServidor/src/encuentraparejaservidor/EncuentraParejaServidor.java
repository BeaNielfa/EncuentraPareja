/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encuentraparejaservidor;

import Conexion.Conexion;
import Hilos.Hilo;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author beani
 */
public class EncuentraParejaServidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here

            ServerSocket servidor = new ServerSocket(1050);
            System.out.println("Servidor iniciado...");
            while (true) {
                Socket cliente = servidor.accept();
                Conexion c = new Conexion();
                Object[] claves = Utilidades.Util.generarClaves();
                Hilo h = new Hilo(cliente, c, claves);
                h.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(EncuentraParejaServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(EncuentraParejaServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hilos;

import Conexion.Conexion;
import Datos.Usuario;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SealedObject;

/**
 *
 * @author beani
 */
public class Hilo extends Thread{
    
    private Socket cliente;
    private Conexion c;
    private PublicKey clavepubl;
    private PrivateKey clavepri;
    private PublicKey clientKey;
    public Hilo(Socket cliente, Conexion c, Object[] claves) {
       this.cliente = cliente;
       this.c = c;
       this.clavepri = (PrivateKey) claves[0];
       this.clavepubl = (PublicKey) claves[1];
    }

    @Override
    public void run() {
        try {
            intercambioClaves();
            DataOutputStream enviar = new DataOutputStream(cliente.getOutputStream());
            DataInputStream recibir = new DataInputStream(cliente.getInputStream());
            
            boolean existe = false;
            int op = recibir.readInt();
            Usuario u = (Usuario) Utilidades.Util.desencriptarObjeto((SealedObject)Utilidades.Util.recibirObjeto(cliente), clavepri);
            switch(op){
                case 0:
                    System.out.println("REGISTRO");
                    break;
                case 1:
                    System.out.println("INICIO SESION");
                    int existeU = c.obtener(u.getEmail(), u.getContraseña());//BUSCAMOS SI EL USUARIO EXISTE
                    if(existeU > 0){
                        existe = true;
                    }
                    enviar.writeBoolean(existe);
                    break;
                        
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   private void intercambioClaves() throws IOException, ClassNotFoundException {
        clientKey = (PublicKey) Utilidades.Util.recibirObjeto(cliente);
        Utilidades.Util.enviarObject(cliente, clavepubl);
    }
    
    
}

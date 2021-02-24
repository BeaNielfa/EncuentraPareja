/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encuentraparejacliente;

import Ventanas.FrmLogin;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author beani
 */
public class EncuentraParejaCliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         
        
        try {

            InetAddress dir = InetAddress.getLocalHost();
            Socket servidor = new Socket(dir, 1050);
            
            Object[] claves = Utilidades.Util.generarClaves();
             
            Utilidades.Util.enviarObject(servidor,claves[1]);
            PublicKey serverKey = (PublicKey) Utilidades.Util.recibirObjeto(servidor);
    
            FrmLogin fl = new FrmLogin (servidor, claves, serverKey);
            fl.setVisible(true);
        } catch (IOException ex) {
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(EncuentraParejaCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EncuentraParejaCliente.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
}

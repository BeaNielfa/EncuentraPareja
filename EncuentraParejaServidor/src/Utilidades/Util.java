/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;

/**
 *
 * @author beani
 */
public class Util {
     public static String Hexadecimal(byte []resumen){
        String hex="";
        for (int i=0;i<resumen.length;i++){
            String h = Integer.toHexString(resumen[i] & 0xFF);
            if (h.length() == 1) hex+=0;
            hex+=h;
        }
        return hex;
    }
    
    public static byte[] firmar(String msg, PrivateKey clavePrivada) throws Exception {        
        Signature dsa = Signature.getInstance("MD5withRSA");
        dsa.initSign(clavePrivada);
        dsa.update(msg.getBytes());
        return dsa.sign(); //Mensaje firmado.
    }

    public static boolean verifica(String msg, PublicKey clavePublica, byte[] firma) throws Exception {
        Signature verifica_dsa = Signature.getInstance("MD5withRSA");
        verifica_dsa.initVerify(clavePublica);

        //msg = "Otra cosa";
        verifica_dsa.update(msg.getBytes());
        return verifica_dsa.verify(firma);
    }

    public static byte[] cifrarSimetrico(String msg,SecretKey clave) throws Exception {
        Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, clave);

        byte[] TextoPlano = msg.getBytes();
        byte[] cifrado = c.doFinal(TextoPlano);
        return cifrado;
    }
    public static String desencriptarSimetrico(byte[] cifrado,SecretKey clave) throws Exception {
        Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, clave);
        byte[] desencriptado=c.doFinal(cifrado);
        return new String(desencriptado);
    }
    
    public static byte[] cifrarAsimetrico(String msg, PublicKey clave) throws Exception{
        Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        c.init(Cipher.ENCRYPT_MODE, clave);
        byte[] TextoPlano = msg.getBytes();
        byte[] cifrado = c.doFinal(TextoPlano);
        return cifrado;
    }
    
    public static String desencriptarAsimetrico(byte[] cifrado,PrivateKey clave) throws Exception {
        Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        c.init(Cipher.DECRYPT_MODE, clave);
        byte[] desencriptado=c.doFinal(cifrado);
        return new String(desencriptado);
    }

    public static SealedObject cifrarObjeto(Object msg, PublicKey clavePublica) throws Exception {
        Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        c.init(Cipher.ENCRYPT_MODE, clavePublica);        
        return new SealedObject((Serializable) msg, c);
    }
    
    public static Object desencriptarObjeto(SealedObject objeto, PrivateKey clavepriv) throws Exception {
        Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        c.init(Cipher.DECRYPT_MODE, clavepriv);        
        return objeto.getObject(c);
    }
    
    public static String resumen (String contraseña) throws NoSuchAlgorithmException{
        
            //MessageDigest md = MessageDigest.getInstance("SHA");
            MessageDigest md = MessageDigest.getInstance("SHA1");
            //MessageDigest md = MessageDigest.getInstance("MD5");
            
            byte datos[] = contraseña.getBytes(); //Texto en bytes
            md.update(datos);                //Se introduce el texto en bytes a resumir
            byte resumen1[] = md.digest();    //Se calcula el resumen
            String resumen = Hexadecimal(resumen1);
            return resumen;
      
       
    }

    
        
    
    public static Object[] generarClaves() throws NoSuchAlgorithmException{
        Object[] claves = new Object[2];
        
        KeyPairGenerator KeyGen = KeyPairGenerator.getInstance("RSA");
        KeyGen.initialize(4096);
        KeyPair par = KeyGen.generateKeyPair();
        PrivateKey clavepriv = par.getPrivate();
        PublicKey clavepubl = par.getPublic();
        
        claves[0] = clavepriv;
        claves[1] = clavepubl;
        
        return claves;
    }
   
    
    public static void enviarObject(Socket receptor, Object objeto){
         try{
             ObjectOutputStream oos = new ObjectOutputStream(receptor.getOutputStream());
             oos.writeObject(objeto);
             System.out.println("Objeto enviado.");
         }catch(IOException e){
             
         }
     }
     
     public static Object recibirObjeto(Socket receptor) throws IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(receptor.getInputStream());
        Object objeto = ois.readObject();
        System.out.println("Objeto recibido");
         return objeto;
     }
    
}



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
import java.util.ArrayList;
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
                    
                    if(existe){
                            String tipo = c.obtenerTipoUser(u.getEmail());//OBTENEMOS EL TIPO DE USUARIO 
                            enviar.writeUTF(tipo);
                            int activado = c.isActivado(u.getEmail());
                            enviar.writeInt(activado);
                           
                            if(tipo.equals("Admin")){
                               
                               
                               
                               ArrayList lu = new ArrayList();
                               lu = c.obtenerUsuariosTablaArrayList();//RECOGEMOS LOS USUARIOS QUE HAY
                               //ENVIAMOS LA LISTA DE USUARIOS
                               Utilidades.Util.enviarObject(cliente, lu);
                               
                                
                               while(recibir.readBoolean()){
                                    int opc = recibir.readInt();
                                    String email ="";
                                    switch (opc){
                                        case 0://ACTIVAR USUARIO
                                            
                                            email = recibir.readUTF();//RECIBIMOS EL EMAIL DEL USUARIO QUE HAY QUE ACTIVAR
                                            c.activarUsuario(email);
                                            
                                            lu = c.obtenerUsuariosTablaArrayList();//RECOGEMOS LOS USUARIOS QUE HAY
                                            
                                            //ENVIAMOS LA LISTA DE USUARIOS
                                            Utilidades.Util.enviarObject(cliente, lu);
                                            break;
                                        case 1://DAR DE BAJA UN USUARIO (ELIMINAR)
                                            
                                            email = recibir.readUTF();//RECIBIMOS EL EMAIL DEL USUARIO QUE HAY QUE DAR DE BAJA
                                            c.eliminarUsuario(email);
                                            lu = c.obtenerUsuariosTablaArrayList();//RECOGEMOS LOS USUARIOS QUE HAY
                                            //ENVIAMOS LA LISTA DE USUARIOS
                                            Utilidades.Util.enviarObject(cliente, lu);
                                            break;
                                        case 2://MODIFICAR UN USUARIO
                                            email = recibir.readUTF();//RECIBIMOS EL EMAIL DEL USUARIO QUE HAY QUE MODIFICAR
                                            Usuario us = c.cogerUsuario(email);//RECOGEMOS SUS DATOS
                                                                                        
                                            Utilidades.Util.enviarObject(cliente, us);
                                            us = (Usuario) Utilidades.Util.recibirObjeto(cliente);
                                           
                                            //ACTUALIZAMOS EL USUARIO EN LA BBDD
                                            c.actualizarUsuario(us);
                                            //Volvemos a instanciar oos para que pierda la cabecera
                                           
                                            lu = c.obtenerUsuariosTablaArrayList();//RECOGEMOS LOS USUARIOS QUE HAY
                                            //ENVIAMOS LA LISTA DE USUARIOS
                                            Utilidades.Util.enviarObject(cliente, lu);
                                            break;
                                        case 3:
                                          u = (Usuario) Utilidades.Util.desencriptarObjeto((SealedObject)Utilidades.Util.recibirObjeto(cliente), clavepri);
                                            
                                            int insert = c.insertarUsuario(u);
                                            boolean insertado = false;
                                            if(insert > 0){//SI SE HA INSERTADO EL USUARIO
                                                String id = c.obtenerId(u.getEmail());
                                                int tipoUser;
                                                if(u.getTipoUser().equals("Admin")){
                                                    tipoUser = 1;
                                                }else{
                                                    tipoUser = 2;
                                                }
                                                int insertR = c.insertarRol(Integer.parseInt(id), tipoUser);//LE PONEMOS EL TIPO DE USUARIO 
                                                if(insertR>0){
                                                    insertado = true;
                                                }
                                            }

                                            enviar.writeBoolean(insertado);//ENVIAMOS SI EL REGISTRO SE HA CREADO CORRECTAMENTE O NO
                                            
                                            
                                            lu = c.obtenerUsuariosTablaArrayList();//RECOGEMOS LOS USUARIOS QUE HAY
                                            //ENVIAMOS LA LISTA DE USUARIOS
                                            Utilidades.Util.enviarObject(cliente, lu);
                                            break;
                                    }
                                    
                                    
                               }
                               
                            }
                            
                        }
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

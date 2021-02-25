/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hilos;

import Conexion.Conexion;
import Datos.Firmas;
import Datos.Preferencias;
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
            boolean insertado  = false;
            boolean verificado = true;
            boolean seguir = true;
            int insert ;
            
            while(seguir){
                
                int op = recibir.readInt();
                Usuario u = (Usuario) Utilidades.Util.desencriptarObjeto((SealedObject)Utilidades.Util.recibirObjeto(cliente), clavepri);
                switch(op){
                    case 0://REGISTRO
                            System.out.println("REGISTRO");

                            Firmas firmas = (Firmas) Utilidades.Util.recibirObjeto(cliente);
                            String ap = Utilidades.Util.desencriptarAsimetrico(firmas.getFirmaA().getMensaje(), clavepri);
                            String nom = Utilidades.Util.desencriptarAsimetrico(firmas.getFirmaN().getMensaje(), clavepri);
                            String em = Utilidades.Util.desencriptarAsimetrico(firmas.getFirmaE().getMensaje(), clavepri);
                            String pass = Utilidades.Util.desencriptarAsimetrico(firmas.getFirmaC().getMensaje(), clavepri);

                            if(Utilidades.Util.verifica(ap, clientKey, firmas.getFirmaA().getFirma())
                                && Utilidades.Util.verifica(nom, clientKey, firmas.getFirmaN().getFirma())
                                && Utilidades.Util.verifica(em, clientKey, firmas.getFirmaE().getFirma())
                                && Utilidades.Util.verifica(pass, clientKey, firmas.getFirmaC().getFirma())){
                                System.out.println("FIRMA VERIFICADA");
                            }else{
                                System.out.println("NO VERIFICADA");
                                verificado = false;
                            }

                            if(verificado){
                                insert = c.insertarUsuario(u);
                                if(insert > 0){//SI SE HA INSERTADO EL USUARIO
                                    String id = c.obtenerId(u.getEmail());
                                    int insertR = c.insertarRol(Integer.parseInt(id), 2);//LE PONEMOS EL TIPO DE USUARIO 
                                    if(insertR>0){
                                        insertado = true;
                                    }
                                }
                            }

                            enviar.writeBoolean(insertado);//ENVIAMOS SI EL REGISTRO SE HA CREADO CORRECTAMENTE O NO
                            
                            if(insertado){
                                String  e = recibir.readUTF();
                                String idUsuario = c.obtenerId(e);

                                Preferencias p = (Preferencias) Utilidades.Util.recibirObjeto(cliente);

                                int insertPreferencias = c.insertarPreferencia(idUsuario, p);

                                if(insertPreferencias >0){
                                    enviar.writeBoolean(true);
                                    seguir= false;//PARA QUE EL BUCLE PARE, PORQUE YA SE HA REGISTRADO
                                }
                            }
                            break;
                    case 1:
                        System.out.println("INICIO SESION");
                        int existeU = c.obtener(u.getEmail(), u.getContraseña());//BUSCAMOS SI EL USUARIO EXISTE
                        if(existeU > 0){
                            existe = true;
                            seguir = false;//PARA QUE EL BUCLE PARE, PORQUE YA HA INICIADO SESION
                        }
                        enviar.writeBoolean(existe);//ENVIAMOS SI EXISTE O NO 

                        if(existe){//SI EL USUARIO EXISTE
                                
                                String tipo = c.obtenerTipoUser(u.getEmail());//OBTENEMOS EL TIPO DE USUARIO 
                                enviar.writeUTF(tipo);
                                int activado = c.isActivado(u.getEmail());//OBTENEMOS SI ESTA ACTIVADO O NO 
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

                                                //ACTUALIZAMOS LA LISTA
                                                lu = c.obtenerUsuariosTablaArrayList();//RECOGEMOS LOS USUARIOS QUE HAY
                                                //ENVIAMOS LA LISTA DE USUARIOS
                                                Utilidades.Util.enviarObject(cliente, lu);
                                                break;
                                            case 1://DAR DE BAJA UN USUARIO (ELIMINAR)

                                                email = recibir.readUTF();//RECIBIMOS EL EMAIL DEL USUARIO QUE HAY QUE DAR DE BAJA
                                                c.eliminarUsuario(email);
                                                //ACTUALIZAMOS LA LISTA
                                                lu = c.obtenerUsuariosTablaArrayList();//RECOGEMOS LOS USUARIOS QUE HAY
                                                //ENVIAMOS LA LISTA DE USUARIOS
                                                Utilidades.Util.enviarObject(cliente, lu);
                                                break;
                                            case 2://MODIFICAR UN USUARIO
                                                email = recibir.readUTF();//RECIBIMOS EL EMAIL DEL USUARIO QUE HAY QUE MODIFICAR
                                                Usuario us = c.cogerUsuario(email);//RECOGEMOS SUS DATOS

                                                //MANDAMOS EL USUARIO PARA QUE PUEDA TENER ACCESO A SUS DATOS
                                                Utilidades.Util.enviarObject(cliente, us);
                                                
                                                //RECIBIMOS EL USUARIO CON LOS CAMBIOS
                                                us = (Usuario) Utilidades.Util.recibirObjeto(cliente);

                                                //ACTUALIZAMOS EL USUARIO EN LA BBDD
                                                c.actualizarUsuario(us);
                                                
                                                //ACTUALIZAMOS LA LISTA
                                                lu = c.obtenerUsuariosTablaArrayList();//RECOGEMOS LOS USUARIOS QUE HAY
                                                //ENVIAMOS LA LISTA DE USUARIOS
                                                Utilidades.Util.enviarObject(cliente, lu);
                                                break;
                                            case 3://INSERTAR UN USUARIO 
                                              //RECIBIMOS EL USUARIO A INSERTAR
                                              u = (Usuario) Utilidades.Util.desencriptarObjeto((SealedObject)Utilidades.Util.recibirObjeto(cliente), clavepri);

                                                insert = c.insertarUsuario(u);//LO INSERTAMOS
                                                insertado = false;
                                                if(insert > 0){//SI SE HA INSERTADO EL USUARIO
                                                    String id = c.obtenerId(u.getEmail());
                                                    int tipoUser;
                                                    if(u.getTipoUser().equals("Admin")){
                                                        tipoUser = 1;
                                                    }else{
                                                        tipoUser = 2;
                                                    }
                                                    //INSERTAMOS EL ROL
                                                    int insertR = c.insertarRol(Integer.parseInt(id), tipoUser);//LE PONEMOS EL TIPO DE USUARIO 
                                                    if(insertR>0){
                                                        insertado = true;
                                                    }
                                                }
                                                //ENVIAMOS SI TODO HA IDO BIEN O NO
                                                enviar.writeBoolean(insertado);//ENVIAMOS SI EL REGISTRO SE HA CREADO CORRECTAMENTE O NO

                                                //ACTUALIZAMOS LA LISTA
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

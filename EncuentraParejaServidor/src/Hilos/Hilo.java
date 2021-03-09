/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hilos;

import Conexion.Conexion;
import Datos.Firma;
import Datos.Preferencias;
import Datos.Privilegios;
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
public class Hilo extends Thread {

    private Socket cliente;
    private Conexion c;
    private PublicKey clavepubl;
    private PrivateKey clavepri;
    private PublicKey clientKey;
    private boolean seguir = true;

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
            
            DataInputStream recibir = new DataInputStream(cliente.getInputStream());
            while (seguir) {

                int op = recibir.readInt();
                Usuario u = (Usuario) Utilidades.Util.desencriptarObjeto((SealedObject) Utilidades.Util.recibirObjeto(cliente), clavepri);
                String idPrincipal = c.obtenerIdUsuario(u.getEmail());
                switch (op) {
                    case 0://REGISTRO
                        System.out.println("REGISTRO");
                        registrarUsuario(u);
                        break;
                    case 1:
                        System.out.println("INICIO SESION");
                        iniciarSesion(u, idPrincipal);
                        break;
                }
            }
            System.out.println("CLIENTE DESCONECTADO");

        } catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private void iniciarSesion(Usuario u, String idPrincipal){
        
         
        try {
            DataOutputStream enviar = new DataOutputStream(cliente.getOutputStream());
            DataInputStream recibir = new DataInputStream(cliente.getInputStream());
            
            boolean existe = false;
            int existeU = c.comprobarExisteUsuario(u.getEmail(), u.getContraseña());//BUSCAMOS SI EL USUARIO EXISTE
            if (existeU > 0) {
                existe = true;
                seguir = false;//PARA QUE EL BUCLE PARE, PORQUE YA HA INICIADO SESION
            }   enviar.writeBoolean(existe);//ENVIAMOS SI EXISTE O NO 
            if (existe) {//SI EL USUARIO EXISTE
                
                String tipo = c.obtenerTipoUser(idPrincipal);//OBTENEMOS EL TIPO DE USUARIO
                //enviar.writeUTF(tipo);
                Utilidades.Util.enviarObject(cliente, Utilidades.Util.cifrarAsimetrico(tipo, clientKey));
                int activado = c.isActivado(idPrincipal);//OBTENEMOS SI ESTA ACTIVADO O NO
                //enviar.writeInt(activado);
                Utilidades.Util.enviarObject(cliente, Utilidades.Util.cifrarAsimetrico(String.valueOf(activado), clientKey));
                //enviar.writeUTF(idPrincipal);
                Utilidades.Util.enviarObject(cliente, Utilidades.Util.cifrarAsimetrico(idPrincipal, clientKey));
                if (tipo.equals("Admin")) {
                    
                    Privilegios pr = c.cogerPrivilegios(idPrincipal);
                    //Utilidades.Util.enviarObject(cliente, pr);
                    Utilidades.Util.enviarObject(cliente, Utilidades.Util.cifrarObjeto(pr, clientKey));
                    
                    
                    listaUsers(idPrincipal,0);
                    
                    while (recibir.readBoolean()) {
                        int opc = recibir.readInt();
                        String email = "";
                        switch (opc) {
                            case 0://ACTIVAR USUARIO
                                activarUsuario(idPrincipal);
                                
                                break;
                            case 1://DAR DE BAJA UN USUARIO (ELIMINAR)
                                bajaUsuario(idPrincipal);
                                break;
                            case 2://MODIFICAR UN USUARIO
                                //email = recibir.readUTF();//RECIBIMOS EL EMAIL DEL USUARIO QUE HAY QUE MODIFICAR
                                modificarPerfilDesdeAdmin(idPrincipal);
                                break;
                            case 3://INSERTAR UN USUARIO ADMINISTRADOR
                                insertAdmin(idPrincipal);
                                break;
                            case 4://CAMBIAR PRIVILEGIOS
                                cambiarPrivilegios(idPrincipal);
                                break;
                        }
                        
                    }
                    
                }else if(tipo.equals("User")){//CUANDO INICIA SESION UN USUARIO NORMAL
                    Preferencias pre = c.cogerPreferencias(idPrincipal);//RECOGEMOS SUS PREFERENCIAS INICIALES
                    ArrayList us = c.listaUsuariosGustos(idPrincipal, pre);//PARA MOSTRARLE USUARIOS SEGUN SUS GUSTOS
                    Utilidades.Util.enviarObject(cliente, us);//ENVIAMOS LA LISTA DE USUARIOS
                    
                    while (recibir.readBoolean()) {
                        int opc = recibir.readInt();//RECIBIMOS OPCION
                        
                        switch (opc){
                            case 0://MOSTRAR TODOS LOS USUARIOS QUE HAY EN LA APLICACION
                                listaUsers(idPrincipal,1);
                                break;
                            case 1://MODIFICAR NUESTRO PERFIL
                                modificarPerfil(idPrincipal);
                                break;
                            case 2://ACTUALIZAR PREFERENCIAS
                                actualizarPreferencias(idPrincipal);
                                break;
                            case 3://LIKE
                                likeOrNoLike(idPrincipal);
                                break;
                            case 4://MOSTRAR QUE USUARIOS ME GUSTAN Y A CUALES LES GUSTO YO
                                listarLikes(idPrincipal);
                                break;
                            case 5://VOLVER AL FRM INICO MOSTRANDO LA TABLA LIKES
                                volverInicio(idPrincipal);
                                break;
                            case 6://BORRAR LIKE
                                borrarLike(idPrincipal);
                                break;
                            case 7://DAR LIKE
                                darLike(idPrincipal);
                                break;
                            case 8://LISTAR AMIGOS
                               listarAmigos(idPrincipal);
                               break;
                        }
                    }
                    
                }
            }   
        } catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    private void activarUsuario(String idPrincipal){
        try {
            //RECIBIMOS EL EMAIL DEL USUARIO QUE HAY QUE ACTIVAR
            String email = Utilidades.Util.desencriptarAsimetrico((byte[]) Utilidades.Util.recibirObjeto(cliente), clavepri);
            String idAct = c.obtenerIdUsuario(email);
            int activado = c.isActivado(idAct);
            c.activarUsuario(email, activado);
            
            //ACTUALIZAMOS LA LISTA
            listaUsers(idPrincipal,0);
        } catch (Exception ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private void bajaUsuario(String idPrincipal){
        try {
            //RECIBIMOS EL EMAIL DEL USUARIO QUE HAY QUE DAR DE BAJA
            String email = Utilidades.Util.desencriptarAsimetrico((byte[]) Utilidades.Util.recibirObjeto(cliente), clavepri);
            c.eliminarUsuario(email);
            //ACTUALIZAMOS LA LISTA
            listaUsers(idPrincipal,0);
        } catch (Exception ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void insertAdmin(String idPrincipal){
        try {
            DataOutputStream enviar = new DataOutputStream(cliente.getOutputStream());
            //RECIBIMOS EL USUARIO A INSERTAR
            Usuario u = (Usuario) Utilidades.Util.desencriptarObjeto((SealedObject) Utilidades.Util.recibirObjeto(cliente), clavepri);
            int insert = 0;
            boolean insertado = false;
            
            insert = c.insertarUsuario(u);//LO INSERTAMOS
            insertado = false;
            if (insert > 0) {//SI SE HA INSERTADO EL USUARIO
                String id = c.obtenerIdUsuario(u.getEmail());
                
                int insertR = c.insertarRol(Integer.parseInt(id), 1);//LE PONEMOS EL TIPO DE USUARIO
                int priv = c.insertarPrivilegios(id);
                if (priv > 0) {
                    insertado = true;
                }
            }
            //ENVIAMOS SI TODO HA IDO BIEN O NO
            enviar.writeBoolean(insertado);//ENVIAMOS SI EL REGISTRO SE HA CREADO CORRECTAMENTE O NO
            
            //Utilidades.Util.enviarObject(cliente, pr);
            Privilegios pr = c.cogerPrivilegios(idPrincipal);
            Utilidades.Util.enviarObject(cliente, Utilidades.Util.cifrarObjeto(pr, clientKey));
            //ACTUALIZAMOS LA LISTA
            listaUsers(idPrincipal, 0);
        } catch (Exception ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void cambiarPrivilegios(String idPrincipal){
        try {
            //email = recibir.readUTF();//RECIBIMOS EL EMAIL DEL USUARIO QUE HAY CAMBIARLE LOS PRIVILEGIOS
            String email = Utilidades.Util.desencriptarAsimetrico((byte[]) Utilidades.Util.recibirObjeto(cliente), clavepri);
            String id = c.obtenerIdUsuario(email);
            
            Privilegios priv = c.cogerPrivilegios(id);
            //Utilidades.Util.enviarObject(cliente, priv);//ENVIAMOS LOS PRIVILEGIOS QUE TIENE ESE USUARIO
            Utilidades.Util.enviarObject(cliente, Utilidades.Util.cifrarObjeto(priv, clientKey));
            
            //priv = (Privilegios) Utilidades.Util.recibirObjeto(cliente);
            priv = (Privilegios) Utilidades.Util.desencriptarObjeto((SealedObject) Utilidades.Util.recibirObjeto(cliente), clavepri);
            c.actualizarPrivilegios(priv, id);
            
            //Utilidades.Util.enviarObject(cliente, pr);
            Privilegios pr = c.cogerPrivilegios(idPrincipal);
            Utilidades.Util.enviarObject(cliente, Utilidades.Util.cifrarObjeto(pr, clientKey));
            
            listaUsers(idPrincipal, 0);
            
        } catch (Exception ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void listaUsers(String idPrincipal,int tipo){
        ArrayList lu = c.obtenerListaUsuarios(idPrincipal,tipo);//RECOGEMOS LA LISTA, 1 ES PARA MOSTRAR LOS USUARIOS QUE NO SEAN ADMIN
        Utilidades.Util.enviarObject(cliente, lu);//EMVIAMOS LA LISTA
    }
    
    private void modificarPerfil(String idPrincipal){
        try {
            Usuario u = c.cogerUsuario(idPrincipal);//COGEMOS NUESTRA INFORMACION
            Utilidades.Util.enviarObject(cliente, Utilidades.Util.cifrarObjeto(u, clientKey));//LE ENVIAMOS LOS DATOS
            u = (Usuario) Utilidades.Util.desencriptarObjeto((SealedObject) Utilidades.Util.recibirObjeto(cliente), clavepri);
            
            //ACTUALIZAMOS EL USUARIO EN LA BBDD
            c.actualizarUsuario(u, idPrincipal);
            
            //RECOGEMOS LAS PREFERENCIAS Y ACTUALIZAMOS LA LISTA
            Preferencias pre = c.cogerPreferencias(idPrincipal);
            listaGustos(pre,idPrincipal);
        } catch (Exception ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void modificarPerfilDesdeAdmin(String idPrincipal){
        try {
            String email = Utilidades.Util.desencriptarAsimetrico((byte[]) Utilidades.Util.recibirObjeto(cliente), clavepri);
            String idMod = c.obtenerIdUsuario(email);
            Usuario us = c.cogerUsuario(idMod);//RECOGEMOS SUS DATOS
            
            //MANDAMOS EL USUARIO PARA QUE PUEDA TENER ACCESO A SUS DATOS
            Utilidades.Util.enviarObject(cliente, Utilidades.Util.cifrarObjeto(us, clientKey));
            //RECIBIMOS EL USUARIO CON LOS CAMBIOS
            us = (Usuario) Utilidades.Util.desencriptarObjeto((SealedObject) Utilidades.Util.recibirObjeto(cliente), clavepri);
            
            //ACTUALIZAMOS EL USUARIO EN LA BBDD
            c.actualizarUsuario(us, idMod);
            
            
            //Utilidades.Util.enviarObject(cliente, pr);
            Privilegios pr = c.cogerPrivilegios(idPrincipal);
            Utilidades.Util.enviarObject(cliente, Utilidades.Util.cifrarObjeto(pr, clientKey));
            //ACTUALIZAMOS LA LISTA
            listaUsers(idPrincipal,0);
        } catch (Exception ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void actualizarPreferencias(String idPrincipal){
        try {
            Preferencias pre = c.cogerPreferencias(idPrincipal);//RECOGEMOS LAS PREFERENCIAS
            Utilidades.Util.enviarObject(cliente, Utilidades.Util.cifrarObjeto(pre, clientKey));
            
            //RECOGEMOS LAS PREFERENCIAS CAMBIADAS
            Preferencias pref = (Preferencias) Utilidades.Util.desencriptarObjeto((SealedObject) Utilidades.Util.recibirObjeto(cliente), clavepri);
            c.actualizarPreferencias(pref, idPrincipal);//ACTUALIZAMOS
            
            
            pre = c.cogerPreferencias(idPrincipal);//VOLVEMOS A COGER LAS PREFERENCIAS
            listaGustos(pre,idPrincipal);
        } catch (Exception ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void listaGustos(Preferencias pre, String idPrincipal){
        ArrayList us = new ArrayList();
        us = c.listaUsuariosGustos(idPrincipal, pre);//RECOGEMOS LA LISTA
        Utilidades.Util.enviarObject(cliente, us);//ENVIAMOS LA LISTA
    }
    private void listarLikes(String idPrincipal){
        //USUARIOS QUE ME GUSTAN
        ArrayList likes = new ArrayList();
        likes = c.listaUsuariosLikes(idPrincipal,0);
        Utilidades.Util.enviarObject(cliente, likes);

        //USUARIOS QUE LES GUSTO
        likes = c.listaUsuariosLikes(idPrincipal,1);
        Utilidades.Util.enviarObject(cliente, likes);
    }
    private void volverInicio (String idPrincipal){
        Preferencias pre = c.cogerPreferencias(idPrincipal);
        ArrayList us = new ArrayList();
        us = c.listaUsuariosGustos(idPrincipal, pre);
        Utilidades.Util.enviarObject(cliente, us);
    }
    
    private void borrarLike(String idPrincipal){
        try {
            String email = Utilidades.Util.desencriptarAsimetrico((byte[]) Utilidades.Util.recibirObjeto(cliente), clavepri);
            String idLike = c.obtenerIdUsuario(email);
            
            c.borrarLike(idPrincipal, idLike);
            
            ArrayList likes = new ArrayList();
            likes = c.listaUsuariosLikes(idPrincipal,0);
            Utilidades.Util.enviarObject(cliente, likes);
        } catch (Exception ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void darLike(String idPrincipal){
        try {
            DataOutputStream enviar = new DataOutputStream(cliente.getOutputStream());
            
            
            String email = Utilidades.Util.desencriptarAsimetrico((byte[]) Utilidades.Util.recibirObjeto(cliente), clavepri);
            String idLike = c.obtenerIdUsuario(email);
            int gustan = c.seGustan(idPrincipal, idLike);
            if(gustan >0){//SI YA LE GUSTABA INDICARA QUE EL USUARIO YA LE GUSTA
                
                enviar.writeInt(0);
            }else{//SI NO INSERTARA EL LIKE EN LA BBDD
                enviar.writeInt(1);
                c.insertarLike(idPrincipal, idLike);
            }
            
            //COMPROBAMOS SI AMBOS SE GUSTAN PARA QUE SE HAGAN AMIGOS
            gustan = c.seGustan(idPrincipal, idLike);
            int  gustanLike = c.seGustan(idLike, idPrincipal);
            if(gustan > 0 && gustanLike >0){
                
                c.hacerseAmigos(idPrincipal, idLike);
            }
            
            ArrayList likes = new ArrayList();
            likes = c.listaUsuariosLikes(idPrincipal,0);
            Utilidades.Util.enviarObject(cliente, likes);
        } catch (IOException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void likeOrNoLike (String idPrincipal){
        try {
            String email = Utilidades.Util.desencriptarAsimetrico((byte[]) Utilidades.Util.recibirObjeto(cliente), clavepri);
            String idLike = c.obtenerIdUsuario(email);
            int gustan = c.seGustan(idPrincipal, idLike);//COMPROBAMOS SI LE GUSTA O NO
            
            if(gustan > 0){//SI YA LE GUSTABA
                c.borrarLike(idPrincipal, idLike);//QUITAMOS EL LIKE
            }else{//SI NO LE GUSTA
                c.insertarLike(idPrincipal, idLike);//HACEMOS LIKE
            }
            //COMPROBAMOS SI AMBOS SE GUSTAN PARA QUE SE HAGAN AMIGOS
            gustan = c.seGustan(idPrincipal, idLike);
            int gustanLike = c.seGustan(idLike, idPrincipal);
            if(gustan > 0 && gustanLike >0){
                
                c.hacerseAmigos(idPrincipal, idLike);
            }
        } catch (Exception ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void listarAmigos(String idPrincipal){
        ArrayList amigos = new ArrayList();
        amigos = c.obtenerAmigos(idPrincipal);
        Utilidades.Util.enviarObject(cliente, amigos);
        
    }
    
    private void registrarUsuario(Usuario u){
        try {
            DataOutputStream enviar = new DataOutputStream(cliente.getOutputStream());
            boolean verificado = true;
            int insert =0;
            String idUsuarioNuevo ="";
            boolean insertado = false;
            System.out.println("REGISTRO");
            
            //RECOGEMOS LA FIRMAS
            Firma[] firmas = (Firma[]) Utilidades.Util.recibirObjeto(cliente);
            //DESENCRIPTO LOS MENSAJES
            String ap = Utilidades.Util.desencriptarAsimetrico(firmas[1].getMensaje(), clavepri);
            String nom = Utilidades.Util.desencriptarAsimetrico(firmas[0].getMensaje(), clavepri);
            String em = Utilidades.Util.desencriptarAsimetrico(firmas[2].getMensaje(), clavepri);
            String pass = Utilidades.Util.desencriptarAsimetrico(firmas[3].getMensaje(), clavepri);
            
            //VERIFICO SI LA FIRMA ES CORRECTA
            if (Utilidades.Util.verifica(ap, clientKey, firmas[1].getFirma())
                    && Utilidades.Util.verifica(nom, clientKey, firmas[0].getFirma())
                    && Utilidades.Util.verifica(em, clientKey, firmas[2].getFirma())
                    && Utilidades.Util.verifica(pass, clientKey, firmas[3].getFirma())) {
                System.out.println("FIRMA VERIFICADA");
            } else {
                System.out.println("NO VERIFICADA");
                verificado = false;
            }
            
            if (verificado) {//SI ESTA VERIFICADA
                insert = c.insertarUsuario(u);//INSERTAMOS EL USUARIO
                idUsuarioNuevo = c.obtenerIdUsuario(u.getEmail());
                if (insert > 0) {//SI SE HA INSERTADO EL USUARIO
                    
                    int insertR = c.insertarRol(Integer.parseInt(idUsuarioNuevo), 2);//LE PONEMOS EL TIPO DE USUARIO
                    if (insertR > 0) {
                        insertado = true;
                    }
                }
            }
            
            enviar.writeBoolean(insertado);//ENVIAMOS SI EL REGISTRO SE HA CREADO CORRECTAMENTE O NO
            
            if (insertado) {
                
                Preferencias p = (Preferencias) Utilidades.Util.desencriptarObjeto((SealedObject) Utilidades.Util.recibirObjeto(cliente), clavepri);
                
                int insertPreferencias = c.insertarPreferencia(idUsuarioNuevo, p);
                
                if (insertPreferencias > 0) {
                    enviar.writeBoolean(true);
                    seguir = false;//PARA QUE EL BUCLE PARE, PORQUE YA SE HA REGISTRADO
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

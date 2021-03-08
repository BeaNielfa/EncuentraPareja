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
            boolean insertado = false;
            boolean verificado = true;
            boolean seguir = true;
            int insert;
            String idUsuarioNuevo = "";
            while (seguir) {

                int op = recibir.readInt();
                Usuario u = (Usuario) Utilidades.Util.desencriptarObjeto((SealedObject) Utilidades.Util.recibirObjeto(cliente), clavepri);
                String idPrincipal = c.obtenerId(u.getEmail());
                switch (op) {
                    case 0://REGISTRO
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
                            idUsuarioNuevo = c.obtenerId(u.getEmail());
                            if (insert > 0) {//SI SE HA INSERTADO EL USUARIO
                                
                                int insertR = c.insertarRol(Integer.parseInt(idUsuarioNuevo), 2);//LE PONEMOS EL TIPO DE USUARIO 
                                if (insertR > 0) {
                                    insertado = true;
                                }
                            }
                        }

                        enviar.writeBoolean(insertado);//ENVIAMOS SI EL REGISTRO SE HA CREADO CORRECTAMENTE O NO

                        if (insertado) {
                            
                            Preferencias p = (Preferencias) Utilidades.Util.recibirObjeto(cliente);

                            int insertPreferencias = c.insertarPreferencia(idUsuarioNuevo, p);

                            if (insertPreferencias > 0) {
                                enviar.writeBoolean(true);
                                seguir = false;//PARA QUE EL BUCLE PARE, PORQUE YA SE HA REGISTRADO
                            }
                        }
                        break;
                    case 1:
                        System.out.println("INICIO SESION");
                        int existeU = c.obtener(u.getEmail(), u.getContraseña());//BUSCAMOS SI EL USUARIO EXISTE
                        if (existeU > 0) {
                            existe = true;
                            seguir = false;//PARA QUE EL BUCLE PARE, PORQUE YA HA INICIADO SESION
                        }
                        enviar.writeBoolean(existe);//ENVIAMOS SI EXISTE O NO 

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
                                
                                
                                ArrayList lu = new ArrayList();
                                lu = c.obtenerUsuariosTablaArrayList(idPrincipal,0);//RECOGEMOS LOS USUARIOS QUE HAY
                                //ENVIAMOS LA LISTA DE USUARIOS
                                Utilidades.Util.enviarObject(cliente, lu);
                                //Utilidades.Util.enviarObject(cliente, Utilidades.Util.cifrarObjeto(lu, clavepubl));

                                while (recibir.readBoolean()) {
                                    int opc = recibir.readInt();
                                    String email = "";
                                    switch (opc) {
                                        case 0://ACTIVAR USUARIO

                                            //email = recibir.readUTF();//RECIBIMOS EL EMAIL DEL USUARIO QUE HAY QUE ACTIVAR
                                            email = Utilidades.Util.desencriptarAsimetrico((byte[]) Utilidades.Util.recibirObjeto(cliente), clavepri);
                                            String idAct = c.obtenerId(email);
                                            activado = c.isActivado(idAct);
                                            c.activarUsuario(email, activado);

                                            //ACTUALIZAMOS LA LISTA
                                            lu = c.obtenerUsuariosTablaArrayList(idPrincipal,0);//RECOGEMOS LOS USUARIOS QUE HAY
                                            //ENVIAMOS LA LISTA DE USUARIOS
                                            Utilidades.Util.enviarObject(cliente, lu);
                                            break;
                                        case 1://DAR DE BAJA UN USUARIO (ELIMINAR)

                                            //email = recibir.readUTF();//RECIBIMOS EL EMAIL DEL USUARIO QUE HAY QUE DAR DE BAJA
                                            email = Utilidades.Util.desencriptarAsimetrico((byte[]) Utilidades.Util.recibirObjeto(cliente), clavepri);
                                            c.eliminarUsuario(email);
                                            //ACTUALIZAMOS LA LISTA
                                            lu = c.obtenerUsuariosTablaArrayList(idPrincipal,0);//RECOGEMOS LOS USUARIOS QUE HAY
                                            //ENVIAMOS LA LISTA DE USUARIOS
                                            Utilidades.Util.enviarObject(cliente, lu);
                                            break;
                                        case 2://MODIFICAR UN USUARIO
                                            //email = recibir.readUTF();//RECIBIMOS EL EMAIL DEL USUARIO QUE HAY QUE MODIFICAR
                                            email = Utilidades.Util.desencriptarAsimetrico((byte[]) Utilidades.Util.recibirObjeto(cliente), clavepri);
                                            String idMod = c.obtenerId(email);
                                            Usuario us = c.cogerUsuario(idMod);//RECOGEMOS SUS DATOS

                                            //MANDAMOS EL USUARIO PARA QUE PUEDA TENER ACCESO A SUS DATOS
                                            //Utilidades.Util.enviarObject(cliente, us);
                                            Utilidades.Util.enviarObject(cliente, Utilidades.Util.cifrarObjeto(us, clientKey));
                                            //RECIBIMOS EL USUARIO CON LOS CAMBIOS
                                            //us = (Usuario) Utilidades.Util.recibirObjeto(cliente);
                                            us = (Usuario) Utilidades.Util.desencriptarObjeto((SealedObject) Utilidades.Util.recibirObjeto(cliente), clavepri);
                                            
                                            //ACTUALIZAMOS EL USUARIO EN LA BBDD
                                            c.actualizarUsuario(us, idMod);
                                            
                                            
                                            //Utilidades.Util.enviarObject(cliente, pr);
                                            pr = c.cogerPrivilegios(idPrincipal);
                                            Utilidades.Util.enviarObject(cliente, Utilidades.Util.cifrarObjeto(pr, clientKey));
                                            //ACTUALIZAMOS LA LISTA
                                            lu = c.obtenerUsuariosTablaArrayList(idPrincipal,0);//RECOGEMOS LOS USUARIOS QUE HAY
                                            //ENVIAMOS LA LISTA DE USUARIOS
                                            Utilidades.Util.enviarObject(cliente, lu);
                                            break;
                                        case 3://INSERTAR UN USUARIO ADMINISTRADOR
                                            //RECIBIMOS EL USUARIO A INSERTAR
                                            u = (Usuario) Utilidades.Util.desencriptarObjeto((SealedObject) Utilidades.Util.recibirObjeto(cliente), clavepri);

                                            insert = c.insertarUsuario(u);//LO INSERTAMOS
                                            insertado = false;
                                            if (insert > 0) {//SI SE HA INSERTADO EL USUARIO
                                                String id = c.obtenerId(u.getEmail());
                                                
                                                int insertR = c.insertarRol(Integer.parseInt(id), 1);//LE PONEMOS EL TIPO DE USUARIO 
                                                int priv = c.insertarPrivilegios(id);
                                                if (priv > 0) {
                                                    insertado = true;
                                                }
                                            }
                                            //ENVIAMOS SI TODO HA IDO BIEN O NO
                                            enviar.writeBoolean(insertado);//ENVIAMOS SI EL REGISTRO SE HA CREADO CORRECTAMENTE O NO
                                            
                                            //Utilidades.Util.enviarObject(cliente, pr);
                                            pr = c.cogerPrivilegios(idPrincipal);
                                            Utilidades.Util.enviarObject(cliente, Utilidades.Util.cifrarObjeto(pr, clientKey));
                                            //ACTUALIZAMOS LA LISTA
                                            lu = c.obtenerUsuariosTablaArrayList(idPrincipal,0);//RECOGEMOS LOS USUARIOS QUE HAY
                                            //ENVIAMOS LA LISTA DE USUARIOS
                                            Utilidades.Util.enviarObject(cliente, lu);
                                            break;
                                        case 4://CAMBIAR PRIVILEGIOS
                                            //email = recibir.readUTF();//RECIBIMOS EL EMAIL DEL USUARIO QUE HAY CAMBIARLE LOS PRIVILEGIOS
                                            email = Utilidades.Util.desencriptarAsimetrico((byte[]) Utilidades.Util.recibirObjeto(cliente), clavepri);
                                            String id = c.obtenerId(email);
                                            
                                            Privilegios priv = c.cogerPrivilegios(id);
                                            //Utilidades.Util.enviarObject(cliente, priv);//ENVIAMOS LOS PRIVILEGIOS QUE TIENE ESE USUARIO
                                            Utilidades.Util.enviarObject(cliente, Utilidades.Util.cifrarObjeto(priv, clientKey));
                                            
                                            //priv = (Privilegios) Utilidades.Util.recibirObjeto(cliente);
                                            priv = (Privilegios) Utilidades.Util.desencriptarObjeto((SealedObject) Utilidades.Util.recibirObjeto(cliente), clavepri);
                                            c.actualizarPrivilegios(priv, id);
                                            
                                            //Utilidades.Util.enviarObject(cliente, pr);
                                            pr = c.cogerPrivilegios(idPrincipal);
                                            Utilidades.Util.enviarObject(cliente, Utilidades.Util.cifrarObjeto(pr, clientKey));
                                            
                                            lu = c.obtenerUsuariosTablaArrayList(idPrincipal,0);//RECOGEMOS LOS USUARIOS QUE HAY
                                            //ENVIAMOS LA LISTA DE USUARIOS
                                            Utilidades.Util.enviarObject(cliente, lu);
                                            break;
                                    }

                                }

                            }else if(tipo.equals("User")){//CUANDO INICIA SESION UN USUARIO NORMAL
                               Preferencias pre = c.cogerPreferencias(idPrincipal);//RECOGEMOS SUS PREFERENCIAS INICIALES
                               ArrayList us = c.obtenerUsuariosTablaGustosArrayList(idPrincipal, pre);//PARA MOSTRARLE USUARIOS SEGUN SUS GUSTOS
                               Utilidades.Util.enviarObject(cliente, us);//ENVIAMOS LA LISTA DE USUARIOS
                               
                                while (recibir.readBoolean()) {
                                    int opc = recibir.readInt();//RECIBIMOS OPCION
                                
                                    switch (opc){
                                        case 0://MOSTRAR TODOS LOS USUARIOS QUE HAY EN LA APLICACION
                                            ArrayList lu = c.obtenerUsuariosTablaArrayList(idPrincipal,1);//RECOGEMOS LA LISTA, 1 ES PARA MOSTRAR LOS USUARIOS QUE NO SEAN ADMIN
                                            Utilidades.Util.enviarObject(cliente, lu);//EMVIAMOS LA LISTA
                                            break;
                                        case 1://MODIFICAR NUESTRO PERFIL
                                            u = c.cogerUsuario(idPrincipal);//COGEMOS NUESTRA INFORMACION
                                            //Utilidades.Util.enviarObject(cliente, u);//LA ENVIAMOS PARA PODER VERLA
                                            Utilidades.Util.enviarObject(cliente, Utilidades.Util.cifrarObjeto(u, clientKey));
                                            //u = (Usuario) Utilidades.Util.recibirObjeto(cliente);//RECIBIMOS EL OBJETO CON LOS CAMBIOS
                                            u = (Usuario) Utilidades.Util.desencriptarObjeto((SealedObject) Utilidades.Util.recibirObjeto(cliente), clavepri);
                                            
                                            //ACTUALIZAMOS EL USUARIO EN LA BBDD
                                            c.actualizarUsuario(u, idPrincipal);
                                            
                                            
                                            pre = c.cogerPreferencias(idPrincipal);//VOLVEMOS A COGER LAS PREFERENCIAS
                                            us = c.obtenerUsuariosTablaGustosArrayList(idPrincipal, pre);//RECOGEMOS LA LISTA
                                            Utilidades.Util.enviarObject(cliente, us);//ENVIAMOS LA LISTA
                                            break;
                                        case 2://ACTUALIZAR PREFERENCIAS
                                            pre = c.cogerPreferencias(idPrincipal);//RECOGEMOS LAS PREFERENCIAS 
                                            Utilidades.Util.enviarObject(cliente, pre);//LAS MANDAMOS PARA PODER VERLAS
                                            
                                            Preferencias pref = (Preferencias) Utilidades.Util.recibirObjeto(cliente);//RECOGEMOS LAS PREFERENCIAS CAMBIADAS
                                            c.actualizarPreferencias(pref, idPrincipal);//ACTUALIZAMOS
                                            
                                            
                                            pre = c.cogerPreferencias(idPrincipal);//VOLVEMOS A COGER LAS PREFERENCIAS
                                            us = c.obtenerUsuariosTablaGustosArrayList(idPrincipal, pre);//RECOGEMOS LA LISTA
                                            Utilidades.Util.enviarObject(cliente, us);//ENVIAMOS LA LISTA
                                            break;
                                        case 3://LIKE
                                            
                                            String email = recibir.readUTF();
                                            String idLike = c.obtenerId(email);
                                            int gustan = c.seGustan(idPrincipal, idLike);
                                            
                                            if(gustan > 0){
                                                c.borrarLike(idPrincipal, idLike);
                                            }else{
                                                c.insertarLike(idPrincipal, idLike);
                                            }
                                            gustan = c.seGustan(idPrincipal, idLike);
                                            int gustanLike = c.seGustan(idLike, idPrincipal);
                                            if(gustan > 0 && gustanLike >0){
                                                
                                                c.hacerseAmigos(idPrincipal, idLike);
                                            }
                                            break;
                                        case 4://MOSTRAR QUE USUARIOS ME GUSTAN Y A CUALES LES GUSTO YO
                                            ArrayList likes = new ArrayList();
                                            likes = c.obtenerUsuariosLikesTablaArrayList(idPrincipal,0);
                                            Utilidades.Util.enviarObject(cliente, likes);
                                            
                                            likes = c.obtenerUsuariosLikesTablaArrayList(idPrincipal,1);
                                            Utilidades.Util.enviarObject(cliente, likes);
                                            break;
                                        case 5://VOLVER AL FRM INICO MOSTRANDO LA TABLA LIKES
                                            pre = c.cogerPreferencias(idPrincipal);
                                            us = c.obtenerUsuariosTablaGustosArrayList(idPrincipal, pre);
                                            Utilidades.Util.enviarObject(cliente, us);
                                            break;
                                        case 6://BORRAR LIKE
                                            email = recibir.readUTF();
                                            idLike = c.obtenerId(email);
                                            
                                            c.borrarLike(idPrincipal, idLike);
                                            
                                            likes = c.obtenerUsuariosLikesTablaArrayList(idPrincipal,0);
                                            Utilidades.Util.enviarObject(cliente, likes);
                                            break;
                                        case 7:
                                            
                                            email = recibir.readUTF();
                                            idLike = c.obtenerId(email);
                                            gustan = c.seGustan(idPrincipal, idLike);
                                            if(gustan >0){
                                                
                                                enviar.writeInt(0);
                                            }else{
                                                enviar.writeInt(1);
                                                c.insertarLike(idPrincipal, idLike);
                                            }
                                            likes = c.obtenerUsuariosLikesTablaArrayList(idPrincipal,0);
                                            Utilidades.Util.enviarObject(cliente, likes);
                                            break;
                                        case 8:
                                            ArrayList amigos = new ArrayList();
                                            amigos = c.obtenerAmigos(idPrincipal);
                                            Utilidades.Util.enviarObject(cliente, amigos);
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

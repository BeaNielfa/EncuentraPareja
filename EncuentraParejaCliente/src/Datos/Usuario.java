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
public class Usuario implements Serializable{
    private String nombre;
   private String apellidos;
   private String email;
   private String contraseña;
   private int activado;
   private String tipoUser;
   private String foto;

   //CONSTRUCTOR QUE UTILIZO PARA EL LOGIN
    public Usuario (String email, String contraseña){
        this.email = email;
        this.contraseña = contraseña;
    }
    
    //CONSTRUCTOR QUE UTILIZO PARA MODIFICAR UN USUARIO
    public Usuario (String nombre, String apellidos, String email){
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        //this.foto = foto;
    }
    
    //CONSTRUCTOR QUE UTILIZO PARA REGISTRAR UN NUEVO USUARIO
    public Usuario (String nombre, String apellidos, String email, String contraseña){
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.contraseña = contraseña;
        
    }
   
   //CONSTRUZTOR QUE UTILIZO PARA MOSTRAR LA LISTA DE USUARIOS
    public Usuario(String nombre, String apellidos, String email, int activado, String tipo) {
       this.nombre = nombre;
       this.apellidos = apellidos;
       this.email = email;
       this.activado = activado;
       this.tipoUser = tipo;
    }
   
    //CONSTRUCTOR QUE SE USA CUANDO SE REGISTRA A UN USUARIO NUEVO
    public Usuario(String nombre, String apellidos, String email, String contraseña,int activado) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.contraseña = contraseña;
        this.activado = activado;
    }

    //CONSTRUCTOR QUE UTILIZO PARA AÑADIR UN USUARIO DESDE LOS ADMINISTRADORES
    public Usuario(String nombre, String apellidos, String email,String contraseña, int activado, String tipo) {
       this.nombre = nombre;
       this.apellidos = apellidos;
       this.email = email;
       this.contraseña = contraseña;
       this.activado = activado;
       this.tipoUser = tipo;
    }

   

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int getActivado() {
        return activado;
    }

    public void setActivado(int activado) {
        this.activado = activado;
    }

    public String getTipoUser() {
        return tipoUser;
    }

    public void setTipoUser(String tipoUser) {
        this.tipoUser = tipoUser;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}

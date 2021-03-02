package Conexion;


import Datos.Preferencias;
import Datos.Privilegios;
import Datos.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Conexion {

    //********************* Atributos *************************
    private java.sql.Connection Conex;
    //Atributo a través del cual hacemos la conexión física.
    private java.sql.Statement Sentencia_SQL;
    //Atributo que nos permite ejecutar una sentencia SQL
    private java.sql.ResultSet Conj_Registros;
    //(Cursor) En él están almacenados los datos.

    //********************** Constructores **************************
    //----------------------------------------------------------
    public Conexion() {
        
    }

    public void abrirConexion() {
        try {
            //Cargar el driver/controlador
            String controlador = "com.mysql.jdbc.Driver";
            //String controlador = "oracle.jdbc.driver.OracleDriver";
            //String controlador = "sun.jdbc.odbc.JdbcOdbcDriver"; 
            //String controlador = "org.mariadb.jdbc.Driver"; // MariaDB la version libre de MySQL (requiere incluir la librería jar correspondiente).
            //Class.forName(controlador).newInstance();
            Class.forName(controlador);
            String URL_BD = "jdbc:mysql://localhost/" + Constantes.bbdd;
            //String URL_BD = "jdbc:mariadb://"+this.servidor+":"+this.puerto+"/"+this.bbdd+"";
            //String URL_BD = "jdbc:oracle:oci:@REPASO";
            //String URL_BD = "jdbc:oracle:oci:@REPASO";
            //String URL_BD = "jdbc:odbc:REPASO";

            //Realizamos la conexión a una BD con un usuario y una clave.
            Conex = java.sql.DriverManager.getConnection(URL_BD, Constantes.usuario, Constantes.passwd);
            Sentencia_SQL = Conex.createStatement();
            System.out.println("Conexion realizada con éxito");
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    //********************** Métodos **************************
    //----------------------------------------------------------
    public int obtener(String nombre, String pass) {
        this.abrirConexion();
        
        //CONSULTA PARA SABER SI UN USUARIO EXISTE O NO 
        String Sentencia = "SELECT * FROM " + Constantes.TablaUsuario+" WHERE EMAIL = '"+nombre+"' AND CONTRASEÑA = '"+pass+"'";
        int total = 0;
        try {
            Conj_Registros = Sentencia_SQL.executeQuery(Sentencia);
            
            while (Conj_Registros.next()){
                
                total++;
             }
           
        } catch (SQLException ex) {
            
        }
        this.cerrarConexion();
        return total;
    }

    //----------------------------------------------------------
    public String obtenerId(String email) {
        this.abrirConexion();
        String id ="";
        //OBTENEMOS EL ID DE UN USUARIO A TRAVÉS DEL EMAIL
        String Sentencia = "SELECT ID FROM " + Constantes.TablaUsuario+" WHERE EMAIL = '"+email+"'";
        
        try {
            Conj_Registros = Sentencia_SQL.executeQuery(Sentencia);
            Conj_Registros.first();
            id = Conj_Registros.getString(1);
        } catch (SQLException ex) {
            
        }
        this.cerrarConexion();
        return id;
    }
    
    public void activarUsuario(String email, int activado){
        try {
            this.abrirConexion();
            if(activado == 0){
                activado = 1;
            }else{
                activado = 0;
            }
            String sentencia = "UPDATE usuarios SET activado = "+activado+" WHERE email ='"+email+"'";
            Sentencia_SQL.executeUpdate(sentencia);
            this.cerrarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void actualizarUsuario (Usuario usuario, String email){
        try {
            //PRIMERO OBTENEMOS EL ID DE ESE USUARIO
            String id = obtenerId(email);
            int tipo ;
            this.abrirConexion();
            
            String update1 = "UPDATE USUARIOS SET NOMBRE = '"+usuario.getNombre()+"' , "
                    + "APELLIDOS = '"+usuario.getApellidos()+"' , "
                    +"EMAIL = '"+usuario.getEmail()+"' WHERE ID = "+id;
            
            
            Sentencia_SQL.executeUpdate(update1);
            
            
            this.cerrarConexion();
                    
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //----------------------------------------------------------
    public String obtenerTipoUser(String id){
        
            
        String tipo = "";
        

        this.abrirConexion();
        //CONSULTA QUE DEVUELVE SI UN USUARIO ES ADMIN O USER
        String Sentencia ="SELECT tiporol.descripcion from rolsasignados, tiporol, usuarios"
                + " WHERE usuarios.id = rolsasignados.idUser AND rolsasignados.idRol = tiporol.id"
                + " AND usuarios.id = "+ id;
            
        try{    
            Conj_Registros = Sentencia_SQL.executeQuery(Sentencia);
            Conj_Registros.first();
            tipo = Conj_Registros.getString(1);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.cerrarConexion();
            
        return tipo;
        
    }

    //----------------------------------------------------------
    public int isActivado(String id){
        
            
        int tipo = 0;
        

        this.abrirConexion();
        //CONSULTA QUE DEVUELVE SI UN USUARIO ES ADMIN O USER
        String Sentencia ="SELECT ACTIVADO FROM USUARIOS WHERE ID = "+id;
            
        try{    
            Conj_Registros = Sentencia_SQL.executeQuery(Sentencia);
            Conj_Registros.first();
            tipo = Conj_Registros.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.cerrarConexion();
            
        return tipo;
        
    }    
    
    //----------------------------------------------------------
    public int insertarUsuario(Usuario u) {
       int cont = 0;
       int contS = 0;
        try {
            this.abrirConexion();
            String Select = "SELECT * FROM " + Constantes.TablaUsuario+" WHERE EMAIL = '"+u.getEmail()+"'";
            Conj_Registros = Sentencia_SQL.executeQuery(Select);
            while (Conj_Registros.next()){
                
                contS++;
            }
           // System.out.println("select"+contS);
            if(contS == 0){
            
                String Sentencia = "INSERT INTO " + Constantes.TablaUsuario + "(nombre, apellidos,email,contraseña, activado) VALUES ( '" + u.getNombre() + "'," + "'" + u.getApellidos() + "','" + u.getEmail() +"','"+u.getContraseña()+"',"+u.getActivado()+ ")";


                cont  =  Sentencia_SQL.executeUpdate(Sentencia);
            }
            
            
            
            
        } catch (SQLException ex) {
            
            
        }
        this.cerrarConexion();
        return cont;
        
    }
    
    
    //----------------------------------------------------------
    public int insertarRol(int idU, int idR) {
       int cont = 0;
        try {
            this.abrirConexion();
            
            String Sentencia = "INSERT INTO rolsasignados (idUser, idRol) VALUES ("+idU+","+idR+ ")";
            
            
            cont  =  Sentencia_SQL.executeUpdate(Sentencia);
            
            
            
            
            this.cerrarConexion();
            
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cont;
        
    }
    
    
     //----------------------------------------------------------
    public int insertarPreferencia(String idU, Preferencias p) {
       int cont = 0;
        try {
            this.abrirConexion();
            
            String Sentencia = "INSERT INTO preferencias (idUser, relacion, deporte, politica, arte, hijos, interes) "
                    + "VALUES ("+idU+", '"+p.getRelacion()+ "' ,"+p.getDeporte()+" , "+p.getPolitica()+" , "+p.getArte()+" , '"+p.getHijos()+"' , '"+p.getInteres()+"')";
            
            
            cont  =  Sentencia_SQL.executeUpdate(Sentencia);
            
            
            
            
            this.cerrarConexion();
            
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cont;
        
    }
    
    public void eliminarUsuario (String email){
        try {
            //PRIMERO OBTENEMOS EL ID DE ESE USUARIO 
            String id = obtenerId(email);
            this.abrirConexion();
            String sentencia1 = "DELETE FROM ROLSASIGNADOS WHERE IDUSER = "+id;
            Sentencia_SQL.executeUpdate(sentencia1);
            String borrarP = "DELETE FROM preferencias WHERE IDUSER = "+id;
            Sentencia_SQL.executeUpdate(borrarP);
            String borrarPr = "DELETE FROM privilegios WHERE IDUSER = "+id;
            Sentencia_SQL.executeUpdate(borrarPr);
            String sentencia = "DELETE FROM USUARIOS WHERE ID = "+id;
            Sentencia_SQL.executeUpdate(sentencia);
            
            this.cerrarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //----------------------------------------------------------
    public ArrayList obtenerUsuariosTablaArrayList(String id) {
        this.abrirConexion();
        ArrayList lp = new ArrayList();
        try {
            String Sentencia = "SELECT usuarios.nombre, usuarios.apellidos, usuarios.email, usuarios.activado,tiporol.descripcion "
                    + "from rolsasignados, tiporol, usuarios" +
                    " WHERE usuarios.id = rolsasignados.idUser "
                    + "AND rolsasignados.idRol = tiporol.id "
                    + "AND usuarios.id <> "+id;;
            Conj_Registros = Sentencia_SQL.executeQuery(Sentencia);
            while (Conj_Registros.next()) {
                lp.add(new Usuario(Conj_Registros.getString(1), Conj_Registros.getString(2), Conj_Registros.getString(3), Conj_Registros.getInt(4), Conj_Registros.getString(5)));
            }
        } catch (SQLException ex) {
        }
        this.cerrarConexion();
        return lp;
    }
    
    public Usuario cogerUsuario (String email){
        Usuario u = null;
        try {
            this.abrirConexion();
            
            String sentencia = "SELECT usuarios.nombre, usuarios.apellidos, usuarios.email "
                    + "FROM usuarios "
                    + "WHERE  usuarios.email = '"+email+"'";
            
            
            Conj_Registros = Sentencia_SQL.executeQuery(sentencia);
            Conj_Registros.first();
            u = new Usuario (Conj_Registros.getString(1),Conj_Registros.getString(2),Conj_Registros.getString(3));
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;
        
    }
     public void actualizarPrivilegios (Privilegios p, String id){
        try {
            
            this.abrirConexion();
            
            String update1 = "UPDATE privilegios SET ACTIVAR = "+p.getActivar()+" , "
                    + "MODIFICAR = "+p.getModificar()+" , "
                    +"ALTA = "+p.getAlta()+" , "+
                    "BAJA = "+p.getBaja()+" , "+
                    "PRIVILEGIOS ="+ p.getPrivilegios()+" WHERE idUser = "+id;
            
            
            Sentencia_SQL.executeUpdate(update1);
            
            
            this.cerrarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     //----------------------------------------------------------
    public int insertarPrivilegios(String id) {
       int cont = 0;
        try {
            this.abrirConexion();
            
            String Sentencia = "INSERT INTO privilegios (idUser, activar, modificar, alta, baja, privilegios) "
                    + "VALUES ("+id+" , "+1+" , "+0+" , "+0+" , "+0+" , "+0+")";
            
            
            cont  =  Sentencia_SQL.executeUpdate(Sentencia);
            
            
            
            
            this.cerrarConexion();
            
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cont;
        
    }
    
     public Privilegios cogerPrivilegios (String id){
        Privilegios u = null;
        try {
            this.abrirConexion();
            
            String sentencia = "SELECT activar, modificar, alta, baja, privilegios "
                    + "FROM privilegios "
                    + "WHERE  idUser = "+id+"";
            
            
            Conj_Registros = Sentencia_SQL.executeQuery(sentencia);
            Conj_Registros.first();
            u = new Privilegios (Conj_Registros.getInt(1),Conj_Registros.getInt(2),Conj_Registros.getInt(3),Conj_Registros.getInt(4),Conj_Registros.getInt(5));
            
            this.cerrarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;
        
    }
    
    //---------------------------------------------------------
    public void cerrarConexion() {
        try {
            // resultado.close();
            this.Conex.close();
            System.out.println("Desconectado de la Base de Datos"); // Opcional para seguridad
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error de Desconexion", JOptionPane.ERROR_MESSAGE);
        }
    }

}

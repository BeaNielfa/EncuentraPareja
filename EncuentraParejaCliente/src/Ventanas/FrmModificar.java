/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import Datos.Usuario;
import java.awt.Image;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SealedObject;
import javax.swing.ImageIcon;

/**
 *
 * @author beani
 */
public class FrmModificar extends javax.swing.JFrame {
   
    private Socket servidor;
    private PublicKey serverKey;
    private Object[] claves;
    private String email;
    private String id;
    
    /**
     * Creates new form FrmModificar
     */
    public FrmModificar(Socket servidor, Object[] claves, PublicKey serverKey, String email, String id) throws IOException, ClassNotFoundException, Exception {
        initComponents();
        this.servidor = servidor;
        this.claves = claves;
        this.serverKey = serverKey;
        this.email  = email;
        this.id = id;
        
        //Centra la ventana en el monitor
        setLocationRelativeTo(null);


        Image imgIcon = new ImageIcon (getClass().getResource("/Imagenes/ico.png")).getImage();
        setIconImage(imgIcon);
        
        
        
        //DataOutputStream dos = new DataOutputStream(servidor.getOutputStream());
        //dos.writeUTF(email);
        Utilidades.Util.enviarObject(servidor, Utilidades.Util.cifrarAsimetrico(email, serverKey));

        //Usuario u = (Usuario) Utilidades.Util.recibirObjeto(servidor);
        Usuario u = (Usuario) Utilidades.Util.desencriptarObjeto((SealedObject)Utilidades.Util.recibirObjeto(servidor), (PrivateKey) claves[0]);

        txtNombre.setText(u.getNombre());
        txtApellidos.setText(u.getApellidos());
        txtEmail.setText(u.getEmail());
        
        
    }
    
    public FrmModificar(Socket servidor, Object[] claves, PublicKey serverKey) throws IOException, ClassNotFoundException, Exception {
        initComponents();
        this.servidor = servidor;
        this.claves = claves;
        this.serverKey = serverKey;
        this.email = email;
        this.id = id;
        //Centra la ventana en el monitor
        setLocationRelativeTo(null);

        Image imgIcon = new ImageIcon(getClass().getResource("/Imagenes/ico.png")).getImage();
        setIconImage(imgIcon);

       

        //Usuario u = (Usuario) Utilidades.Util.recibirObjeto(servidor);
        Usuario u = (Usuario) Utilidades.Util.desencriptarObjeto((SealedObject)Utilidades.Util.recibirObjeto(servidor), (PrivateKey) claves[0]);
        txtNombre.setText(u.getNombre());
        txtApellidos.setText(u.getApellidos());
        txtEmail.setText(u.getEmail());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtApellidos = new javax.swing.JTextField();
        btnAceptar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        btnFoto = new javax.swing.JButton();
        lblFoto = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(204, 153, 255));

        jLabel1.setText("Nombre:");

        jLabel2.setText("Apellidos:");

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setText("Modificación");

        jLabel6.setText("Email:");

        btnFoto.setText("Elegir Foto");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6)
                    .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnFoto)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel5)
                        .addComponent(txtNombre)
                        .addComponent(txtApellidos, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                        .addComponent(txtEmail))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(btnAceptar)))
                .addContainerGap(113, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel5)
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnFoto)
                        .addGap(41, 41, 41)
                        .addComponent(btnAceptar))
                    .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        try {
            // TODO add your handling code here:
            String nombre = (txtNombre.getText());
            String apellidos = (txtApellidos.getText());
            String em = txtEmail.getText();
            
            Usuario u = new Usuario(nombre, apellidos, em);
            //Utilidades.Util.enviarObject(servidor, u);
            Utilidades.Util.enviarObject(servidor, Utilidades.Util.cifrarObjeto(u, serverKey));
            
            
            this.setVisible(false);
            if(email == null){
                FrmInicio fi = new FrmInicio(servidor, claves, serverKey);
                fi.setVisible(true);
            }else{
                FrmAdmin f = new FrmAdmin(servidor, claves, serverKey, id);
                f.setVisible(true);
            }
        } catch (IOException ex) {
            Logger.getLogger(FrmModificar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FrmModificar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(FrmModificar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnFoto;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}

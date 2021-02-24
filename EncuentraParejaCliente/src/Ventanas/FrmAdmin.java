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
import java.io.ObjectInputStream;
import java.net.Socket;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author beani
 */
public class FrmAdmin extends javax.swing.JFrame {

    private Socket servidor;
    private PublicKey serverKey;
    private Object[] claves;
    /**
     * Creates new form FrmAdmin
     */
    public FrmAdmin(Socket servidor, Object[] claves, PublicKey serverKey) throws IOException, ClassNotFoundException {
        initComponents();
        this.servidor = servidor;
        this.claves = claves;
        this.serverKey = serverKey;
        
        //Centra la ventana en el monitor
        setLocationRelativeTo(null);
        
        //Icono
        Image imgIcon = new ImageIcon (getClass().getResource("/Imagenes/ico.png")).getImage();
        setIconImage(imgIcon);
       
         
        Image fot = new ImageIcon(getClass().getResource("/Imagenes/addUser.png")).getImage();
        Icon icono = new ImageIcon(fot.getScaledInstance(btnAdd.getWidth(), btnAdd.getHeight(), Image.SCALE_DEFAULT));
        btnAdd.setIcon(icono);
        
        ArrayList lu = (ArrayList) Utilidades.Util.recibirObjeto(servidor);
        
        
        rellenarTabla(lu);
    }
    
    
    private void rellenarTabla(ArrayList lu){
        String[] dato = new String[5];
        String[] titulos = {"Nombre", "Apellidos","Email", "Activado","Tipo"};
            DefaultTableModel formatoTabla = new DefaultTableModel();
            formatoTabla = new DefaultTableModel(null, titulos);
            tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
            //Con el bucle vamos metiendo los datos en la tabla
            for (int i = 0; i < lu.size(); i++) {
                Usuario u = (Usuario) lu.get(i);
                dato[0]=u.getNombre();
                dato[1] = u.getApellidos();
                dato[2] = u.getEmail();
                
                if (u.getActivado() == 1) {//Si es 1
                    dato[3]="Activado";//Pone Si
                } else {//Si no
                    dato[3]="Desactivado";//Pone No
                }
                dato[4] = u.getTipoUser();
                //Añadimos la fila
                formatoTabla.addRow(dato);
            }
            tabla.setModel(formatoTabla);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        btnMod = new javax.swing.JButton();
        btnActivar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 153, 255));

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 1, 36)); // NOI18N
        jLabel1.setText("USUARIOS");

        tabla.setFont(new java.awt.Font("Comic Sans MS", 0, 11)); // NOI18N
        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabla);

        btnMod.setText("Modificar");
        btnMod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModActionPerformed(evt);
            }
        });

        btnActivar.setText("Activar");
        btnActivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActivarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(224, 224, 224)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(103, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnMod)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEliminar)
                                .addGap(113, 113, 113)
                                .addComponent(btnActivar))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(90, 90, 90))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(35, 35, 35)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMod)
                    .addComponent(btnActivar)
                    .addComponent(btnEliminar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModActionPerformed
        // TODO add your handling code here:
        
        try{
            
            int filaseleccionada = tabla.getSelectedRow();
            if (filaseleccionada == -1){
               JOptionPane.showMessageDialog(null, "No ha seleccionado ninguna fila.");
            } else {

                DataOutputStream dos = new DataOutputStream(servidor.getOutputStream());
                dos.writeBoolean(true);
                dos.writeInt(2);//2 MODIFICAR
                String email = (String)tabla.getValueAt(filaseleccionada, 2);
                //System.out.println(email+" Nombre");

                this.setVisible(false);
                FrmModificar fm = new FrmModificar(servidor,claves, serverKey,email);
                fm.setVisible(true);
                    
                     
                   

            }
        } catch (IOException ex) {
            Logger.getLogger(FrmAdmin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FrmAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnModActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        try{
            
            int filaseleccionada = tabla.getSelectedRow();
            if (filaseleccionada == -1){
               JOptionPane.showMessageDialog(null, "No ha seleccionado ninguna fila.");
            } else {

                    DataOutputStream dos = new DataOutputStream(servidor.getOutputStream());
                    dos.writeBoolean(true);
                    dos.writeInt(1);//1 BORRAR
                    
                    String nombre = (String)tabla.getValueAt(filaseleccionada, 2);
                    System.out.println(nombre+" Nombre");

                     dos.writeUTF(nombre);
                     
                    
                    ArrayList lu = (ArrayList) Utilidades.Util.recibirObjeto(servidor);
                    System.out.println("LISTA RECIBIDA");

                    rellenarTabla(lu);

            }
        } catch (IOException ex) {
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FrmAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnActivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActivarActionPerformed
        // TODO add your handling code here:
        try{
            
             int filaseleccionada = tabla.getSelectedRow();
             if (filaseleccionada == -1){
                JOptionPane.showMessageDialog(null, "No ha seleccionado ninguna fila.");
            } else {

                    DataOutputStream dos = new DataOutputStream(servidor.getOutputStream());
                    dos.writeBoolean(true);
                    dos.writeInt(0);//0 ACTIVAR
                    String nombre = (String)tabla.getValueAt(filaseleccionada, 2);
                    System.out.println(nombre+" Nombre");

                     dos.writeUTF(nombre);
                     
                    
                    ArrayList lu = (ArrayList) Utilidades.Util.recibirObjeto(servidor);
                    System.out.println("LISTA RECIBIDA");

                    rellenarTabla(lu);

            }
        } catch (IOException ex) {
            Logger.getLogger(FrmAdmin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FrmAdmin.class.getName()).log(Level.SEVERE, null, ex);
        } 
      
    }//GEN-LAST:event_btnActivarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            DataOutputStream dos = new DataOutputStream(servidor.getOutputStream());
            dos.writeBoolean(false);
        } catch (IOException ex) {
            Logger.getLogger(FrmAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            DataOutputStream dos = new DataOutputStream(servidor.getOutputStream());
            dos.writeBoolean(true);
            dos.writeInt(3);//3 AÑADIR USUARIO
            this.setVisible(false);
            FrmAddUser fa = new FrmAddUser(servidor,claves, serverKey);
            fa.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(FrmAdmin.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }//GEN-LAST:event_btnAddActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActivar;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnMod;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables
}

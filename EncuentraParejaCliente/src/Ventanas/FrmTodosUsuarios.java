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
public class FrmTodosUsuarios extends javax.swing.JFrame {

    private Socket servidor;
    private PublicKey serverKey;
    private Object[] claves;
    /**
     * Creates new form FrmTodosUsuarios
     */
    public FrmTodosUsuarios(Socket servidor, Object[] claves, PublicKey serverKey) throws IOException, ClassNotFoundException {
        initComponents();
        this.servidor = servidor;
        this.claves = claves;
        this.serverKey = serverKey;
        
        //Centra la ventana en el monitor
        setLocationRelativeTo(null);
        
        //Icono
        Image imgIcon = new ImageIcon (getClass().getResource("/Imagenes/ico.png")).getImage();
        setIconImage(imgIcon);
        
        Image like = new ImageIcon(getClass().getResource("/Imagenes/LikeDislike.png")).getImage();
        Icon iconoLike = new ImageIcon(like.getScaledInstance(btnLike.getWidth(), btnLike.getHeight(), Image.SCALE_DEFAULT));
        btnLike.setIcon(iconoLike);
        
        
        Image volver = new ImageIcon(getClass().getResource("/Imagenes/volver.png")).getImage();
        Icon iconoVolver = new ImageIcon(volver.getScaledInstance(btnVolver.getWidth(), btnVolver.getHeight(), Image.SCALE_DEFAULT));
        btnVolver.setIcon(iconoVolver);
        
        
        DataOutputStream dos = new DataOutputStream(servidor.getOutputStream());
        dos.writeBoolean(true);
        dos.writeInt(0);
        
        ArrayList lu = (ArrayList) Utilidades.Util.recibirObjeto(servidor);
        System.out.println("LISTA RECIBIDA");

        rellenarTabla(lu);
    }

     private void rellenarTabla(ArrayList lu){
        String[] dato = new String[2];
        String[] titulos = {"Nombre", "Email"};
            DefaultTableModel formatoTabla = new DefaultTableModel();
            formatoTabla = new DefaultTableModel(null, titulos);
            tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
            //Con el bucle vamos metiendo los datos en la tabla
            for (int i = 0; i < lu.size(); i++) {
                Usuario u = (Usuario) lu.get(i);
                dato[0]=u.getNombre()+" "+u.getApellidos();
                dato[1] = u.getEmail();
                
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();
        btnLike = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 153, 255));

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

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Todos los Usuarios");

        btnVolver.setBorder(null);
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        btnLike.setBorder(null);
        btnLike.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLikeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(209, 209, 209)
                        .addComponent(btnLike, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(169, 169, 169)
                            .addComponent(jLabel1))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(69, 69, 69)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(67, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel1)
                .addGap(36, 36, 36)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLike, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
         try {
            DataOutputStream dos = new DataOutputStream(servidor.getOutputStream());
            dos.writeBoolean(true);
            
            dos.writeInt(5);
            // TODO add your handling code here:
            this.setVisible(false);
            FrmInicio fi = new FrmInicio (servidor, claves, serverKey);
            fi.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(FrmTodosUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FrmTodosUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnVolverActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            DataOutputStream dos = new DataOutputStream(servidor.getOutputStream());
            dos.writeBoolean(false);
        } catch (IOException ex) {
           
        } 
    }//GEN-LAST:event_formWindowClosing

    private void btnLikeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLikeActionPerformed
         try{
            
            int filaseleccionada = tabla.getSelectedRow();
            if (filaseleccionada == -1){
                JOptionPane.showMessageDialog(null, "No ha seleccionado ninguna fila.");
            } else {

                    DataOutputStream dos = new DataOutputStream(servidor.getOutputStream());
                    dos.writeBoolean(true);
                    dos.writeInt(3);//LIKE
                    String email = (String)tabla.getValueAt(filaseleccionada, 1);
                    //dos.writeUTF(email);
                    Utilidades.Util.enviarObject(servidor, Utilidades.Util.cifrarAsimetrico(email, serverKey));
                    
                    JOptionPane.showMessageDialog(null, "Su solicitud ha sido procesada");
            }
        } catch (IOException ex) {
            Logger.getLogger(FrmAdmin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(FrmTodosUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }//GEN-LAST:event_btnLikeActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLike;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import Datos.Preferencias;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author beani
 */
public class FrmPreferencias extends javax.swing.JFrame {

    private Socket servidor;
    private PublicKey serverKey;
    private Object[] claves;
    
    /**
     * Creates new form FrmPreferencias
     */
    public FrmPreferencias(Socket servidor, Object[] claves, PublicKey serverKey) {
        initComponents();
        this.servidor = servidor;
        this.claves = claves;
        this.serverKey = serverKey;
        
        
        //Centra la ventana en el monitor
        setLocationRelativeTo(null);


        Image imgIcon = new ImageIcon (getClass().getResource("/Imagenes/ico.png")).getImage();
        setIconImage(imgIcon);
        
        SpinnerNumberModel modeloSpinner = new SpinnerNumberModel();
        modeloSpinner.setMinimum(0);
        modeloSpinner.setMaximum(100);
        jsDeporte.setModel(modeloSpinner);

        SpinnerNumberModel modeloArte = new SpinnerNumberModel();
        modeloArte.setMinimum(0);
        modeloArte.setMaximum(100);
        jsArte.setModel(modeloArte);
        
        SpinnerNumberModel modeloPolitica = new SpinnerNumberModel();
        modeloPolitica.setMinimum(0);
        modeloPolitica.setMaximum(100);
        jsPolitica.setModel(modeloPolitica);
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupRelacion = new javax.swing.ButtonGroup();
        groupHijos = new javax.swing.ButtonGroup();
        groupInteres = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jrbSeria = new javax.swing.JRadioButton();
        jrbEsporadica = new javax.swing.JRadioButton();
        jsDeporte = new javax.swing.JSpinner();
        jsPolitica = new javax.swing.JSpinner();
        jsArte = new javax.swing.JSpinner();
        jrbTiene = new javax.swing.JRadioButton();
        jrbQuiere = new javax.swing.JRadioButton();
        jrbHijosAmbos = new javax.swing.JRadioButton();
        jrbMujeres = new javax.swing.JRadioButton();
        jrbHombres = new javax.swing.JRadioButton();
        jrbInteresAmbos = new javax.swing.JRadioButton();
        btnAceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 153, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setText("Rellene las Preferencias");

        jLabel2.setText("Relacion:");

        jLabel3.setText("Deporte:");

        jLabel4.setText("Politica:");

        jLabel5.setText("Arte:");

        jLabel6.setText("Hijos:");

        jLabel7.setText("Interes:");

        groupRelacion.add(jrbSeria);
        jrbSeria.setText("Seria:");

        jrbEsporadica.setText("Esporadica");

        groupHijos.add(jrbTiene);
        jrbTiene.setText("Tiene");

        groupHijos.add(jrbQuiere);
        jrbQuiere.setText("Quiere");

        groupHijos.add(jrbHijosAmbos);
        jrbHijosAmbos.setText("Ambos");

        groupInteres.add(jrbMujeres);
        jrbMujeres.setText("Mujeres");

        groupInteres.add(jrbHombres);
        jrbHombres.setText("Hombres");

        groupInteres.add(jrbInteresAmbos);
        jrbInteresAmbos.setText("Ambos");

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jrbSeria)
                                .addGap(18, 18, 18)
                                .addComponent(jrbEsporadica))
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jrbMujeres)
                                        .addGap(18, 18, 18)
                                        .addComponent(jrbHombres)
                                        .addGap(18, 18, 18)
                                        .addComponent(jrbInteresAmbos))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jsPolitica, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jsDeporte))
                                        .addGap(169, 169, 169))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jsArte, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jrbTiene, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addComponent(jrbQuiere)
                                        .addGap(18, 18, 18)
                                        .addComponent(jrbHijosAmbos))))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(202, 202, 202)
                        .addComponent(btnAceptar)))
                .addContainerGap(96, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(44, 44, 44)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jrbSeria)
                            .addComponent(jrbEsporadica))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jsDeporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4))
                    .addComponent(jsPolitica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jsArte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jrbTiene)
                    .addComponent(jrbQuiere)
                    .addComponent(jrbHijosAmbos))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jrbMujeres)
                    .addComponent(jrbHombres)
                    .addComponent(jrbInteresAmbos))
                .addGap(44, 44, 44)
                .addComponent(btnAceptar)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
         try {
            String relacion ="", hijos ="", interes ="";
            int deporte, arte, politica;
            if(jrbSeria.isSelected()){
                relacion = "Seria";
            }else if(jrbEsporadica.isSelected()){
                relacion="Esporadica";
            }   deporte = (Integer) jsDeporte.getValue();
            
            arte = (Integer) jsArte.getValue();
            politica = (Integer) jsPolitica.getValue();
            
            if(jrbTiene.isSelected()){
                hijos="Tiene";
            }else if(jrbQuiere.isSelected()){
                hijos="Quiere";
            }else if(jrbHijosAmbos.isSelected()){
                hijos="Ambos";
            }  
            
            if(jrbMujeres.isSelected()){
                interes="Mujeres";
            }else if(jrbHombres.isSelected()){
                interes="Hombres";
            }else if(jrbInteresAmbos.isSelected()){
                interes="Ambos";
            }   
            
            if(relacion.equals("")||hijos.equals("")||interes.equals("")){
                JOptionPane.showMessageDialog(null, "Faltan campos por rellenar", "Informacion", JOptionPane.ERROR_MESSAGE);
            }  
            
            DataOutputStream dos = new DataOutputStream(servidor.getOutputStream());
            DataInputStream dis = new DataInputStream(servidor.getInputStream());
           
            Preferencias p = new Preferencias(relacion, deporte, politica, arte, hijos, interes);
            Utilidades.Util.enviarObject(servidor, p);
            
            
            boolean insertado = dis.readBoolean();
            
            if(insertado){
                JOptionPane.showMessageDialog(null, "Preferencias Insertadas, ahora debe esperar a ser activado por un Administrador", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }else{
                JOptionPane.showMessageDialog(null, "Ha ocurrido algún error", "Informacion", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            Logger.getLogger(FrmPreferencias.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.ButtonGroup groupHijos;
    private javax.swing.ButtonGroup groupInteres;
    private javax.swing.ButtonGroup groupRelacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jrbEsporadica;
    private javax.swing.JRadioButton jrbHijosAmbos;
    private javax.swing.JRadioButton jrbHombres;
    private javax.swing.JRadioButton jrbInteresAmbos;
    private javax.swing.JRadioButton jrbMujeres;
    private javax.swing.JRadioButton jrbQuiere;
    private javax.swing.JRadioButton jrbSeria;
    private javax.swing.JRadioButton jrbTiene;
    private javax.swing.JSpinner jsArte;
    private javax.swing.JSpinner jsDeporte;
    private javax.swing.JSpinner jsPolitica;
    // End of variables declaration//GEN-END:variables
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package fooddelivery;

import javax.swing.*;
import java.util.Random;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Saathvik
 */
public class payment extends javax.swing.JFrame {
    Connection con;
    Statement st;
    PreparedStatement ps;
    ResultSet rs;
    
    String cust_id;
    String streetAddress;
    String pincode;
    String rest_id;
    String rest_name;
    javax.swing.JTable cart;
    /**
     * Creates new form payment
     */
    public payment() {
        initComponents();
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@SAATHVIK-5590:1521:XE","SYSTEM","oracle");
            } catch (SQLException ex) {
            Logger.getLogger(payment.class.getName()).log(Level.SEVERE,null, ex);
            }
        } catch(ClassNotFoundException ex){
        Logger.getLogger(payment.class.getName()).log(Level.SEVERE,null, ex);
        }
    }
    
    public void setTable(){
        try {
            String sql = "SELECT upi_id FROM upi_details WHERE cust_id=?";
            ps = con.prepareStatement(sql);
            ps.setString(1,this.cust_id);
            rs = ps.executeQuery();

            javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
            };
            
            model.addColumn("UPI ID");

            while (rs.next()) {
                String upi_id = rs.getString("upi_id");
                
                model.addRow(new Object[]{upi_id});
            }

            jTable1.setModel(model);
            jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        } catch (SQLException ex) {
            Logger.getLogger(past_orders.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
    private String getRandomDelvPartnerId(){
        Random random = new Random();
        int randomNumber = random.nextInt(10) + 1; // Generates random number between 1 and 10
        String formattedNumber = String.format("P%03d", randomNumber); // Formats to P001-P010
        return formattedNumber;
        }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "UPI ID"
            }
        ));
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Payment Options");

        jButton1.setText("Place Order");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Logout");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Back to Restaurants");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton2.setText("Add UPI ID");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(273, 273, 273)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(217, 217, 217)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(223, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(96, 96, 96)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        rest_display rd = new rest_display();
        rd.cust_id=this.cust_id;
        rd.streetAddress=this.streetAddress;
        rd.pincode=this.pincode;
        rd.setTable();
        rd.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        login_page lp = new login_page();
        lp.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int selectedRowIndex = jTable1.getSelectedRow();
        javax.swing.table.DefaultTableModel cartModel = (javax.swing.table.DefaultTableModel) this.cart.getModel();
        
        if (selectedRowIndex != -1) { // If a row is selected
        // Get values from the selected row
            //String upi_d = jTable1.getValueAt(selectedRowIndex, 0).toString();
            try{
                String getMaxOrderNoSQL = "SELECT MAX(order_no) FROM orders";
                Statement stmt = con.createStatement();
                ResultSet rs1 = stmt.executeQuery(getMaxOrderNoSQL);

                String maxOrderNo = "O001"; // Default starting order_no if no orders exist yet

                if (rs1.next()) {
                    String currentMaxOrderNo = rs1.getString(1);
                    if (currentMaxOrderNo != null) {
                        int orderNumber = Integer.parseInt(currentMaxOrderNo.substring(1)); // Extract numeric part
                        orderNumber++; // Increment
                        maxOrderNo = String.format("O%03d", orderNumber); // Format back to 'Oxxx'
                    }
                }
                
                String sql = "INSERT INTO orders VALUES (?,?,?,?,SYSTIMESTAMP,NULL,0,NULL)";
                ps=con.prepareStatement(sql);
                ps.setString(1,maxOrderNo);
                ps.setString(2,this.cust_id);
                ps.setString(3,this.rest_id);
                String delv_partner_id = getRandomDelvPartnerId();
                ps.setString(4,delv_partner_id);
                ps.executeUpdate();
                
                String insertOrderListSQL = "INSERT INTO order_list VALUES (?, ?, ?)";
                ps = con.prepareStatement(insertOrderListSQL);

                // Iterate through each row in jTable2
                for (int i = 0; i < cartModel.getRowCount(); i++) {
                    String dish_id = cartModel.getValueAt(i, 0).toString();
                    int quantity = (int) cartModel.getValueAt(i, 2);

                    // Set parameters for the prepared statement
                    ps.setString(1, maxOrderNo); // Assuming getOrderNo() retrieves the current order number
                    ps.setString(2, dish_id);
                    ps.setInt(3, quantity);

                    // Execute the insertion
                    ps.executeUpdate();
                }
                
                JOptionPane.showMessageDialog(this, "Order Placed!");
                
                options2 o = new options2();
                o.cust_id = this.cust_id;
                o.streetAddress = streetAddress;
                o.pincode = pincode;
                o.setVisible(true);
                this.dispose();
                
            } catch (SQLException ex) {
                Logger.getLogger(sign_up_page2.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an UPI ID", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
            add_upi p = new add_upi();
            p.cust_id=this.cust_id;
            p.streetAddress=this.streetAddress;
            p.pincode=this.pincode;
            p.rest_id=this.rest_id;
            p.rest_name=this.rest_name;
            p.cart=this.cart;
            
            p.setVisible(true);
            this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new payment().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}

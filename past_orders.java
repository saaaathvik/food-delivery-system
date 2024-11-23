/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package fooddelivery;

import javax.swing.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Saathvik
 */
public class past_orders extends javax.swing.JFrame {
    Connection con;
    Statement st;
    PreparedStatement ps;
    ResultSet rs;
    
    String cust_id;
    String streetAddress;
    String pincode;

    /**
     * Creates new form past_orders
     */
    public past_orders() {
        initComponents();
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@SAATHVIK-5590:1521:XE","SYSTEM","oracle");
            } catch (SQLException ex) {
            Logger.getLogger(past_orders.class.getName()).log(Level.SEVERE,null, ex);
            }
        } catch(ClassNotFoundException ex){
        Logger.getLogger(past_orders.class.getName()).log(Level.SEVERE,null, ex);
        }
    }
    
    public void setTable(){
        try {
            String sql = "SELECT * FROM orders WHERE cust_id=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, this.cust_id);
            rs = ps.executeQuery();

            javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
            };

            model.addColumn("Order Number");
            model.addColumn("Restaurant");
            model.addColumn("Delivery Partner");
            model.addColumn("Order Time");
            model.addColumn("Delivery Time");
            model.addColumn("Bill Amount");

            while (rs.next()) {
                String ono = rs.getString("order_no");
                String rest_id = rs.getString("rest_id");
                String delvPartner = rs.getString("delv_partner_id");
                String order_time = rs.getString("order_time");
                String delv_time = rs.getString("delv_time");
                String bill_amt = rs.getString("bill_amt");

                // Fetch restaurant name
                String sqlRest = "SELECT rest_name FROM restaurant WHERE rest_id=?";
                PreparedStatement psRest = con.prepareStatement(sqlRest);
                psRest.setString(1, rest_id);
                ResultSet rsRest = psRest.executeQuery();
                String rest_name = "";
                if (rsRest.next()) {
                    rest_name = rsRest.getString("rest_name");
                }

                // Fetch delivery partner name
                String sqlDP = "SELECT delivery_partner_name FROM delivery_partner WHERE delv_partner_id=?";
                PreparedStatement psDP = con.prepareStatement(sqlDP);
                psDP.setString(1, delvPartner);
                ResultSet rsDP = psDP.executeQuery();
                String dpname = "";
                if (rsDP.next()) {
                    dpname = rsDP.getString("delivery_partner_name");
                }

                // Add row to table model
                model.addRow(new Object[]{ono, rest_name, dpname, order_time, delv_time, bill_amt});
            }

            jTable1.setModel(model);
            jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        } catch (SQLException ex) {
            Logger.getLogger(past_orders.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Past Orders");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Order Number", "Restaurant", "Delivery Partner", "Order Time", "Delivery Time", "Bill Amount"
            }
        ));
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Logout");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Back to Options");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Dish Name", "Quantity", "Unit Price"
            }
        ));
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(339, 339, 339)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(148, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        login_page lp = new login_page();
        lp.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        options2 o = new options2();
        o.cust_id = this.cust_id;
        o.streetAddress = streetAddress;
        o.pincode = pincode;
        o.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        int selectedRow = jTable1.getSelectedRow();
        
        if(selectedRow!=-1){
            String ono = jTable1.getValueAt(selectedRow, 0).toString();
            
            javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
            };

            model.addColumn("Dish Name");
            model.addColumn("Quantity");
            model.addColumn("Unit Price");

            try{
                String sql = "SELECT dish_id, qty FROM order_list WHERE order_no=?";
                ps=con.prepareStatement(sql);
                ps.setString(1,ono);
                rs=ps.executeQuery();
                
                while (rs.next()){
                   String dish_id = rs.getString("dish_id");
                   String qty = rs.getString("qty");
                   
                   String sqlRest = "SELECT dish_name, unit_price FROM dishes WHERE dish_id=?";
                    PreparedStatement psRest = con.prepareStatement(sqlRest);
                    psRest.setString(1, dish_id);
                    ResultSet rsRest = psRest.executeQuery();
                    String dish_name = "";
                    String unit_price="";
                    if (rsRest.next()) {
                        dish_name = rsRest.getString("dish_name");
                        unit_price = rsRest.getString("unit_price");
                    }
                    
                    model.addRow(new Object[]{dish_name,qty,unit_price});
                }
                
                jTable2.setModel(model);
            } catch (SQLException ex) {
            Logger.getLogger(past_orders.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

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
            java.util.logging.Logger.getLogger(past_orders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(past_orders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(past_orders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(past_orders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new past_orders().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}

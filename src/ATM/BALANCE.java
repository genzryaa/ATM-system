/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ATM;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class BALANCE extends javax.swing.JFrame {
    
    private String accNO = "8801-4492-1029";
    private double currentBalance = 0.0;
    private boolean balanceVisible = true;
    private JButton togglePrivacyBtn;
    
    public BALANCE() {
        initComponents();
        this.currentBalance = Menu.BALANCE;
        buildCleanBalanceInterface();
    }
    public BALANCE (String acc)
    {
        initComponents();
        if (acc != null && !acc.trim().isEmpty()) {
            this.accNO = acc;
        }
        this.currentBalance = Menu.BALANCE;
        buildCleanBalanceInterface();
    }
    
    private void buildCleanBalanceInterface() {
        setTitle("Bintang Bank - Account Balance Inquiry");
        setResizable(false);

        getContentPane().removeAll();
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(18, 18, 18));

        // 1. TOP HEADER BANNER
        JPanel headerPanel = new JPanel(new GridLayout(2, 1, 0, 6));
        headerPanel.setBackground(Color.BLACK);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(255, 204, 51)),
                BorderFactory.createEmptyBorder(18, 25, 18, 25)
        ));

        JLabel titleLbl = new JLabel("ACCOUNT BALANCE INQUIRY", SwingConstants.CENTER);
        titleLbl.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLbl.setForeground(new Color(255, 204, 51));

        JLabel accLbl = new JLabel("ACCOUNT NO: " + accNO + "   |   SAVINGS ACCOUNT", SwingConstants.CENTER);
        accLbl.setFont(new Font("Monospaced", Font.BOLD, 15));
        accLbl.setForeground(new Color(190, 190, 190));

        headerPanel.add(titleLbl);
        headerPanel.add(accLbl);
        getContentPane().add(headerPanel, BorderLayout.NORTH);

        // 2. CENTER CARD AREA
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(18, 18, 18));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 35, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Balance Display Box
        jLabel1.setOpaque(true);
        jLabel1.setBackground(new Color(28, 28, 28));
        jLabel1.setFont(new Font("Monospaced", Font.BOLD, 32));
        jLabel1.setPreferredSize(new Dimension(460, 82));
        jLabel1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 204, 51), 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        updateBalanceDisplay();

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(jLabel1, gbc);

        // HIDE / SHOW Toggle Button directly beside Balance Box
        togglePrivacyBtn = new JButton("HIDE");
        togglePrivacyBtn.setPreferredSize(new Dimension(120, 82));
        togglePrivacyBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        togglePrivacyBtn.setBackground(new Color(50, 50, 50));
        togglePrivacyBtn.setForeground(new Color(255, 204, 51));
        togglePrivacyBtn.setFocusPainted(false);
        togglePrivacyBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        togglePrivacyBtn.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 51), 1));
        togglePrivacyBtn.addActionListener(e -> {
            Toolkit.getDefaultToolkit().beep();
            balanceVisible = !balanceVisible;
            togglePrivacyBtn.setText(balanceVisible ? "HIDE" : "SHOW");
            updateBalanceDisplay();
        });

        gbc.gridx = 1;
        centerPanel.add(togglePrivacyBtn, gbc);

        // Timestamp & Daily Limit Info Row
        String timeStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));
        JLabel infoLbl = new JLabel("As of " + timeStr + "   •   Daily Withdrawal Limit: RM 5,000.00", SwingConstants.CENTER);
        infoLbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
        infoLbl.setForeground(new Color(170, 170, 170));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(6, 10, 22, 10);
        centerPanel.add(infoLbl, gbc);

        // Bottom 3 Action Buttons Row
        JPanel actionRow = new JPanel(new GridLayout(1, 3, 20, 0));
        actionRow.setBackground(new Color(18, 18, 18));
        actionRow.setPreferredSize(new Dimension(600, 54));

        JButton receiptBtn = new JButton("PRINT SLIP");
        receiptBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        receiptBtn.setBackground(new Color(45, 45, 45));
        receiptBtn.setForeground(Color.WHITE);
        receiptBtn.setFocusPainted(false);
        receiptBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        receiptBtn.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 1));
        receiptBtn.addActionListener(e -> showBalanceReceipt());

        JButton quickWithdrawBtn = new JButton("WITHDRAW CASH");
        quickWithdrawBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        quickWithdrawBtn.setBackground(new Color(46, 160, 67));
        quickWithdrawBtn.setForeground(Color.WHITE);
        quickWithdrawBtn.setFocusPainted(false);
        quickWithdrawBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        quickWithdrawBtn.addActionListener(e -> {
            Toolkit.getDefaultToolkit().beep();
            this.dispose();
            new WITHDRAW().setVisible(true);
        });

        jButton1.setText("MAIN MENU");
        jButton1.setFont(new Font("SansSerif", Font.BOLD, 15));
        jButton1.setBackground(new Color(255, 204, 51));
        jButton1.setForeground(Color.BLACK);
        jButton1.setFocusPainted(false);
        jButton1.setCursor(new Cursor(Cursor.HAND_CURSOR));

        actionRow.add(receiptBtn);
        actionRow.add(quickWithdrawBtn);
        actionRow.add(jButton1);

        gbc.gridy = 2;
        gbc.insets = new Insets(5, 10, 5, 10);
        centerPanel.add(actionRow, gbc);

        getContentPane().add(centerPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    private void updateBalanceDisplay() {
        if (balanceVisible) {
            DecimalFormat df = new DecimalFormat("#,##0.00");
            jLabel1.setText("RM " + df.format(currentBalance));
            jLabel1.setForeground(new Color(102, 255, 153));
        } else {
            jLabel1.setText("RM ••••••");
            jLabel1.setForeground(new Color(255, 204, 51));
        }
    }

    private void showBalanceReceipt() {
        Toolkit.getDefaultToolkit().beep();
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

        String receipt = "====================================\n"
                + "           BINTANG BANK ATM         \n"
                + "====================================\n"
                + "DATE/TIME : " + timestamp + "\n"
                + "CARD/ACC  : " + accNO + "\n"
                + "TXN TYPE  : BALANCE INQUIRY\n"
                + "------------------------------------\n"
                + "AVAILABLE : RM " + df.format(currentBalance) + "\n"
                + "====================================\n"
                + "  Thank you for banking with us!    ";

        JOptionPane.showMessageDialog(this, receipt, "ATM Balance Advice", JOptionPane.INFORMATION_MESSAGE);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 204, 51));

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jButton1.setText("MENU");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(126, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(102, 102, 102)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(410, 410, 410)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 99, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(146, 146, 146))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Toolkit.getDefaultToolkit().beep();
        this.dispose();
        new Menu().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(BALANCE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new BALANCE().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}

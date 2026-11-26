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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class DEPOSIT extends javax.swing.JFrame {
    private JTextField customAmountField;
    private JLabel statusLabel;
    private final java.util.List<JButton> interactiveButtons = new java.util.ArrayList<>();
    
    public DEPOSIT() {
        initComponents();
        buildCleanDepositInterface();
    }

    private void buildCleanDepositInterface() {
        setTitle("Bintang Bank - Cash Deposit");
        setResizable(false);

        getContentPane().removeAll();
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(18, 18, 18));

        // 1. TOP HEADER
        JPanel header = new JPanel(new GridLayout(2, 1, 0, 5));
        header.setBackground(Color.BLACK);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(255, 204, 51)),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JLabel title = new JLabel("CASH DEPOSIT", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(new Color(255, 204, 51));

        DecimalFormat df = new DecimalFormat("#,##0.00");
        JLabel balLbl = new JLabel("Current Balance: RM " + df.format(Menu.BALANCE) + "   •   Insert notes into cash slot", SwingConstants.CENTER);
        balLbl.setFont(new Font("Monospaced", Font.BOLD, 15));
        balLbl.setForeground(new Color(102, 255, 153));

        header.add(title);
        header.add(balLbl);
        getContentPane().add(header, BorderLayout.NORTH);

        // 2. MAIN BODY
        JPanel body = new JPanel(new GridBagLayout());
        body.setBackground(new Color(18, 18, 18));
        body.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 15, 10, 15);

        // Left Card: Quick Deposit Presets
        JPanel presetCard = new JPanel(new BorderLayout(0, 15));
        presetCard.setBackground(new Color(26, 26, 26));
        presetCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(65, 65, 65), 1),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        JLabel presetTitle = new JLabel("QUICK DEPOSIT PRESETS", SwingConstants.CENTER);
        presetTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        presetTitle.setForeground(new Color(255, 204, 51));
        presetCard.add(presetTitle, BorderLayout.NORTH);

        JPanel presetGrid = new JPanel(new GridLayout(3, 2, 16, 16));
        presetGrid.setBackground(new Color(26, 26, 26));
        presetGrid.setPreferredSize(new Dimension(340, 220));

        int[] amounts = {100, 300, 500, 800, 1000, 1500};
        for (int amt : amounts) {
            JButton btn = createStyledButton("RM " + amt, new Color(42, 42, 42), new Color(255, 204, 51));
            btn.addActionListener(e -> processDeposit(amt));
            presetGrid.add(btn);
        }
        presetCard.add(presetGrid, BorderLayout.CENTER);

        JButton cancelBtn = createStyledButton("CANCEL / MAIN MENU", new Color(185, 45, 45), Color.WHITE);
        cancelBtn.setPreferredSize(new Dimension(340, 44));
        cancelBtn.addActionListener(e -> {
            Toolkit.getDefaultToolkit().beep();
            this.dispose();
            new Menu().setVisible(true);
        });
        presetCard.add(cancelBtn, BorderLayout.SOUTH);

        gbc.gridx = 0;
        gbc.gridy = 0;
        body.add(presetCard, gbc);

        // Right Card: Custom Deposit Keypad
        JPanel customCard = new JPanel(new BorderLayout(0, 12));
        customCard.setBackground(new Color(26, 26, 26));
        customCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(65, 65, 65), 1),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        JPanel displayTop = new JPanel(new GridLayout(2, 1, 0, 6));
        displayTop.setBackground(new Color(26, 26, 26));

        JLabel customTitle = new JLabel("OTHER AMOUNT (RM)", SwingConstants.CENTER);
        customTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        customTitle.setForeground(new Color(255, 204, 51));

        customAmountField = new JTextField();
        customAmountField.setEditable(false);
        customAmountField.setHorizontalAlignment(SwingConstants.CENTER);
        customAmountField.setFont(new Font("Monospaced", Font.BOLD, 24));
        customAmountField.setBackground(new Color(38, 38, 38));
        customAmountField.setForeground(new Color(102, 255, 153));
        customAmountField.setPreferredSize(new Dimension(280, 44));
        customAmountField.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 51), 2));

        displayTop.add(customTitle);
        displayTop.add(customAmountField);
        customCard.add(displayTop, BorderLayout.NORTH);

        JPanel padGrid = new JPanel(new GridLayout(4, 3, 10, 10));
        padGrid.setBackground(new Color(26, 26, 26));
        padGrid.setPreferredSize(new Dimension(280, 225));

        String[] keys = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "CLEAR", "0", "DEPOSIT"};
        for (String key : keys) {
            Color bg = new Color(55, 55, 55);
            Color fg = Color.WHITE;
            if ("CLEAR".equals(key)) {
                bg = new Color(255, 204, 51);
                fg = Color.BLACK;
            } else if ("DEPOSIT".equals(key)) {
                bg = new Color(46, 160, 67);
                fg = Color.WHITE;
            }
            JButton btn = createStyledButton(key, bg, fg);
            btn.setFont(new Font("SansSerif", Font.BOLD, "DEPOSIT".equals(key) ? 13 : 15));
            btn.addActionListener(e -> handleCustomKey(key));
            padGrid.add(btn);
        }
        customCard.add(padGrid, BorderLayout.CENTER);

        gbc.gridx = 1;
        body.add(customCard, gbc);

        getContentPane().add(body, BorderLayout.CENTER);

        // 3. BOTTOM STATUS BAR
        statusLabel = new JLabel("Select a quick deposit amount or tap an amount on the keypad", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
        statusLabel.setForeground(new Color(200, 200, 200));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 18, 10));
        getContentPane().add(statusLabel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(new Color(90, 90, 90), 1));
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (btn.isEnabled()) btn.setBackground(bg.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (btn.isEnabled()) btn.setBackground(bg);
            }
        });
        interactiveButtons.add(btn);
        return btn;
    }

    private void handleCustomKey(String key) {
        Toolkit.getDefaultToolkit().beep();
        String cur = customAmountField.getText();
        if ("CLEAR".equals(key)) {
            customAmountField.setText("");
        } else if ("DEPOSIT".equals(key)) {
            if (cur.isEmpty()) {
                statusLabel.setForeground(new Color(255, 102, 102));
                statusLabel.setText("Enter an amount on the keypad first!");
                return;
            }
            double amt = Double.parseDouble(cur);
            if (amt <= 0) {
                statusLabel.setForeground(new Color(255, 102, 102));
                statusLabel.setText("Please enter an amount greater than RM 0.");
                return;
            }
            if (amt > 50000) {
                statusLabel.setForeground(new Color(255, 102, 102));
                statusLabel.setText("Maximum single deposit limit is RM 50,000.");
                return;
            }
            processDeposit(amt);
        } else {
            if (cur.length() < 5 && !(cur.isEmpty() && "0".equals(key))) {
                customAmountField.setText(cur + key);
            }
        }
    }

    private void processDeposit(double amount) {
        Toolkit.getDefaultToolkit().beep();
        DecimalFormat df = new DecimalFormat("#,##0.00");

        int confirm = JOptionPane.showConfirmDialog(this,
                "Confirm Cash Deposit of RM " + df.format(amount) + "?",
                "Confirm Deposit", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        for (JButton b : interactiveButtons) b.setEnabled(false);
        statusLabel.setForeground(new Color(255, 204, 51));
        statusLabel.setText("Counting inserted cash notes... Please wait...");

        Timer t = new Timer(1100, e -> {
            Menu.BALANCE += amount;
            Menu.logTransaction("DEPOSIT", amount);
            Toolkit.getDefaultToolkit().beep();

            int receiptChoice = JOptionPane.showConfirmDialog(this,
                    "Deposit of RM " + df.format(amount) + " successful!\n"
                    + "New Balance: RM " + df.format(Menu.BALANCE) + "\n\n"
                    + "Would you like a printed receipt?",
                    "Deposit Successful", JOptionPane.YES_NO_OPTION);

            if (receiptChoice == JOptionPane.YES_OPTION) {
                String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
                String receipt = "====================================\n"
                        + "           BINTANG BANK ATM         \n"
                        + "====================================\n"
                        + "DATE/TIME : " + ts + "\n"
                        + "TXN TYPE  : CASH DEPOSIT\n"
                        + "DEPOSITED : RM " + df.format(amount) + "\n"
                        + "------------------------------------\n"
                        + "NEW BAL   : RM " + df.format(Menu.BALANCE) + "\n"
                        + "====================================";
                JOptionPane.showMessageDialog(this, receipt, "Deposit Receipt", JOptionPane.INFORMATION_MESSAGE);
            }

            this.dispose();
            new BALANCE().setVisible(true);
        });
        t.setRepeats(false);
        t.start();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        label1 = new java.awt.Label();
        jPanel1 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 124, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(255, 204, 51));

        jButton4.setText("500");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("1500");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("300");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton3.setText("1000");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setText("100");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("800");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        label1.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        label1.setText("Please Choose Amount");

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 138, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jButton7.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jButton7.setText("CANCEL");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jButton8.setText("OTHER");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton8)
                                .addGap(28, 28, 28)
                                .addComponent(jButton7)
                                .addGap(111, 111, 111))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(75, 75, 75))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)))
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jButton8))
                .addGap(29, 29, 29))
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        processDeposit(800);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        processDeposit(100);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        processDeposit(1000);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        processDeposit(500);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        processDeposit(1500);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
       processDeposit(300);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        Toolkit.getDefaultToolkit().beep();
        this.dispose();
        new Menu().setVisible(true);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        Toolkit.getDefaultToolkit().beep();
        String value = JOptionPane.showInputDialog(this, "Enter deposit amount (RM):", "Custom Deposit", JOptionPane.QUESTION_MESSAGE);
        if (value == null || value.trim().isEmpty()) {
            return;
        }
        try {
            double amount = Double.parseDouble(value.trim());
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter an amount greater than RM 0.00.", "Invalid Amount", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (amount > 50000) {
                JOptionPane.showMessageDialog(this, "Maximum single cash deposit limit is RM 50,000.00.", "Limit Exceeded", JOptionPane.WARNING_MESSAGE);
                return;
            }
            processDeposit(amount);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter numbers only.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

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
            java.util.logging.Logger.getLogger(DEPOSIT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new DEPOSIT().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private java.awt.Label label1;
    // End of variables declaration//GEN-END:variables

}

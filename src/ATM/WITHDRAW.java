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

public class WITHDRAW extends javax.swing.JFrame {

    private JTextField customAmountField;
    private JLabel statusLabel;
    private final java.util.List<JButton> interactiveButtons = new java.util.ArrayList<>();
    
    public WITHDRAW() {
        initComponents();
        buildCleanWithdrawInterface();
    }

    private void buildCleanWithdrawInterface() {
       setTitle("Bintang Bank - Cash Withdrawal");
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

        JLabel title = new JLabel("CASH WITHDRAWAL", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(new Color(255, 204, 51));

        DecimalFormat df = new DecimalFormat("#,##0.00");
        JLabel balLbl = new JLabel("Available Balance: RM " + df.format(Menu.BALANCE) + "   •   Multiples of RM 10 only", SwingConstants.CENTER);
        balLbl.setFont(new Font("Monospaced", Font.BOLD, 15));
        balLbl.setForeground(new Color(102, 255, 153));

        header.add(title);
        header.add(balLbl);
        getContentPane().add(header, BorderLayout.NORTH);

        // 2. MAIN BODY (Left: Quick Presets | Right: Custom Amount Keypad)
        JPanel body = new JPanel(new GridBagLayout());
        body.setBackground(new Color(18, 18, 18));
        body.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 15, 10, 15);

        // Left Card: Quick Presets
        JPanel presetCard = new JPanel(new BorderLayout(0, 15));
        presetCard.setBackground(new Color(26, 26, 26));
        presetCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(65, 65, 65), 1),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        JLabel presetTitle = new JLabel("FAST CASH PRESETS", SwingConstants.CENTER);
        presetTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        presetTitle.setForeground(new Color(255, 204, 51));
        presetCard.add(presetTitle, BorderLayout.NORTH);

        JPanel presetGrid = new JPanel(new GridLayout(3, 2, 16, 16));
        presetGrid.setBackground(new Color(26, 26, 26));
        presetGrid.setPreferredSize(new Dimension(340, 220));

        int[] amounts = {100, 300, 500, 800, 1000, 1500};
        for (int amt : amounts) {
            JButton btn = createStyledButton("RM " + amt, new Color(42, 42, 42), new Color(255, 204, 51));
            btn.addActionListener(e -> processWithdrawal(amt));
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

        // Right Card: Custom Amount Keypad
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

        String[] keys = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "CLEAR", "0", "WITHDRAW"};
        for (String key : keys) {
            Color bg = new Color(55, 55, 55);
            Color fg = Color.WHITE;
            if ("CLEAR".equals(key)) {
                bg = new Color(255, 204, 51);
                fg = Color.BLACK;
            } else if ("WITHDRAW".equals(key)) {
                bg = new Color(46, 160, 67);
                fg = Color.WHITE;
            }
            JButton btn = createStyledButton(key, bg, fg);
            btn.setFont(new Font("SansSerif", Font.BOLD, "WITHDRAW".equals(key) ? 12 : 15));
            btn.addActionListener(e -> handleCustomKey(key));
            padGrid.add(btn);
        }
        customCard.add(padGrid, BorderLayout.CENTER);

        gbc.gridx = 1;
        body.add(customCard, gbc);

        getContentPane().add(body, BorderLayout.CENTER);

        // 3. BOTTOM STATUS BAR
        statusLabel = new JLabel("Select a fast-cash preset or tap an amount on the keypad", SwingConstants.CENTER);
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
        } else if ("WITHDRAW".equals(key)) {
            if (cur.isEmpty()) {
                statusLabel.setForeground(new Color(255, 102, 102));
                statusLabel.setText("Enter an amount on the keypad first!");
                return;
            }
            double amt = Double.parseDouble(cur);
            if (amt <= 0 || amt % 10 != 0) {
                statusLabel.setForeground(new Color(255, 102, 102));
                statusLabel.setText("Amount must be a multiple of RM 10 (e.g., 20, 50, 120).");
                return;
            }
            if (amt > 5000) {
                statusLabel.setForeground(new Color(255, 102, 102));
                statusLabel.setText("Maximum single withdrawal limit is RM 5,000.");
                return;
            }
            processWithdrawal(amt);
        } else {
            if (cur.length() < 4 && !(cur.isEmpty() && "0".equals(key))) {
                customAmountField.setText(cur + key);
            }
        }
    }

    private String calculateBanknotes(int amount) {
        int n100 = amount / 100;
        int rem = amount % 100;
        int n50 = rem / 50;
        rem %= 50;
        int n20 = rem / 20;
        rem %= 20;
        int n10 = rem / 10;

        StringBuilder notes = new StringBuilder();
        if (n100 > 0) notes.append(n100).append(" x RM100  ");
        if (n50 > 0) notes.append(n50).append(" x RM50  ");
        if (n20 > 0) notes.append(n20).append(" x RM20  ");
        if (n10 > 0) notes.append(n10).append(" x RM10");
        return notes.toString().trim();
    }

    private void processWithdrawal(double amount) {
        Toolkit.getDefaultToolkit().beep();
        DecimalFormat df = new DecimalFormat("#,##0.00");

        if (amount > Menu.BALANCE) {
            statusLabel.setForeground(new Color(255, 102, 102));
            statusLabel.setText("Insufficient funds! Available balance: RM " + df.format(Menu.BALANCE));
            JOptionPane.showMessageDialog(this,
                    "Insufficient Balance!\nRequested: RM " + df.format(amount) + "\nAvailable: RM " + df.format(Menu.BALANCE),
                    "Transaction Declined", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Confirm Cash Withdrawal of RM " + df.format(amount) + "?",
                "Confirm Withdrawal", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        for (JButton b : interactiveButtons) b.setEnabled(false);
        statusLabel.setForeground(new Color(255, 204, 51));
        statusLabel.setText("Dispensing cash notes... Please collect your cash from the tray...");

        Timer t = new Timer(1100, e -> {
            Menu.BALANCE -= amount;
            Menu.logTransaction("WITHDRAWAL", amount);
            Toolkit.getDefaultToolkit().beep();

            String notes = calculateBanknotes((int) amount);
            int receiptChoice = JOptionPane.showConfirmDialog(this,
                    "Please take your cash: RM " + df.format(amount) + "\nNotes Dispensed: " + notes
                    + "\nRemaining Balance: RM " + df.format(Menu.BALANCE) + "\n\nWould you like a printed receipt?",
                    "Cash Dispensed", JOptionPane.YES_NO_OPTION);

            if (receiptChoice == JOptionPane.YES_OPTION) {
                String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
                String receipt = "====================================\n"
                        + "           BINTANG BANK ATM         \n"
                        + "====================================\n"
                        + "DATE/TIME : " + ts + "\n"
                        + "TXN TYPE  : CASH WITHDRAWAL\n"
                        + "AMOUNT    : RM " + df.format(amount) + "\n"
                        + "NOTES     : " + notes + "\n"
                        + "------------------------------------\n"
                        + "REM BAL   : RM " + df.format(Menu.BALANCE) + "\n"
                        + "====================================";
                JOptionPane.showMessageDialog(this, receipt, "Withdrawal Receipt", JOptionPane.INFORMATION_MESSAGE);
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

        canvas1 = new java.awt.Canvas();
        canvas2 = new java.awt.Canvas();
        jPanel1 = new javax.swing.JPanel();
        label1 = new java.awt.Label();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));

        jPanel1.setBackground(new java.awt.Color(255, 204, 51));

        label1.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        label1.setText("Please Choose Amount");

        jButton4.setText("100");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton3.setText("800");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton6.setText("300");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton1.setText("1500");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("500");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton5.setText("1000");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton7.setText("Others");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Cancel");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(306, 306, 306)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(337, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(225, 225, 225)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                        .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(102, 102, 102)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(67, 67, 67))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 114, Short.MAX_VALUE))))
        );

        label1.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(120, 120, 120))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(canvas2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(canvas2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        Toolkit.getDefaultToolkit().beep();
        String value = JOptionPane.showInputDialog(this, "Enter withdrawal amount (multiples of RM 10):", "Custom Withdrawal", JOptionPane.QUESTION_MESSAGE);
        if (value == null || value.trim().isEmpty()) {
            return;
        }
        try {
            double amount = Double.parseDouble(value.trim());
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter an amount greater than RM 0.", "Invalid Amount", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (amount % 10 != 0) {
                JOptionPane.showMessageDialog(this, "ATM only dispenses notes in multiples of RM 10 (e.g., 20, 50, 120).", "Invalid Denomination", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (amount > 5000) {
                JOptionPane.showMessageDialog(this, "Maximum single withdrawal limit is RM 5,000.00.", "Limit Exceeded", JOptionPane.WARNING_MESSAGE);
                return;
            }
            processWithdrawal(amount);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter numbers only.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        processWithdrawal(500);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        processWithdrawal(300);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       processWithdrawal(100);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        processWithdrawal(800);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        processWithdrawal(1000);       
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        processWithdrawal(1500); 
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        Toolkit.getDefaultToolkit().beep();
        this.dispose();
        new Menu().setVisible(true);
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
            java.util.logging.Logger.getLogger(WITHDRAW.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new WITHDRAW().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Canvas canvas1;
    private java.awt.Canvas canvas2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JPanel jPanel1;
    private java.awt.Label label1;
    // End of variables declaration//GEN-END:variables
}

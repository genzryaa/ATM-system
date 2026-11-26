/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates.
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
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 *
 * @author acer
 */
public class Next extends javax.swing.JFrame {

    private JTextField accField;
    private JPasswordField pinDisplayField;
    private JLabel feedbackLabel;
    private int failedAttempts = 0;
    private static final int MAX_ATTEMPTS = 3;
    private static final String VALID_PIN = "1234";
    private JButton[] keypadButtons;
    
    public Next() {
        initComponents();
        buildCleanATMInterface();
    }

    private void buildCleanATMInterface() {
        setTitle("Bintang Bank - Card & PIN Verification");
        setResizable(false);

        // Replace NetBeans' mixed GroupLayout with a clean BorderLayout
        getContentPane().removeAll();
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(18, 18, 18));

        // 1. TOP HEADER BANNER
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.BLACK);
        headerPanel.setPreferredSize(new Dimension(1100, 105));
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(255, 204, 51)));

        JLabel bankTitle = new JLabel("BINTANG BANK", SwingConstants.CENTER);
        bankTitle.setFont(new Font("Engravers MT", Font.ITALIC, 46));
        bankTitle.setForeground(new Color(255, 204, 51));
        headerPanel.add(bankTitle, BorderLayout.CENTER);
        getContentPane().add(headerPanel, BorderLayout.NORTH);

        // 2. MAIN BODY (Left: Card & PIN Form | Right: Keypad)
        JPanel mainBody = new JPanel(new GridBagLayout());
        mainBody.setBackground(new Color(18, 18, 18));
        mainBody.setBorder(BorderFactory.createEmptyBorder(25, 45, 35, 45));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 15, 10, 15);

        // --- LEFT CARD PANEL ---
        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(new Color(26, 26, 26));
        formCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(65, 65, 65), 1),
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));

        GridBagConstraints fg = new GridBagConstraints();
        fg.fill = GridBagConstraints.HORIZONTAL;
        fg.insets = new Insets(10, 8, 10, 8);

        // Title inside Left Card
        JLabel sectionTitle = new JLabel("CARD AUTHENTICATION", SwingConstants.CENTER);
        sectionTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        sectionTitle.setForeground(new Color(255, 204, 51));
        fg.gridx = 0;
        fg.gridy = 0;
        fg.gridwidth = 2;
        fg.insets = new Insets(0, 0, 20, 0);
        formCard.add(sectionTitle, fg);

        // Row 1: Account Number
        fg.gridwidth = 1;
        fg.insets = new Insets(10, 8, 10, 15);
        fg.gridx = 0;
        fg.gridy = 1;
        JLabel accLbl = new JLabel("CARD / ACC NO");
        accLbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        accLbl.setForeground(Color.WHITE);
        formCard.add(accLbl, fg);

        fg.gridx = 1;
        accField = new JTextField("8801-4492-1029");
        accField.setPreferredSize(new Dimension(280, 42));
        accField.setFont(new Font("Monospaced", Font.BOLD, 17));
        accField.setHorizontalAlignment(SwingConstants.CENTER);
        accField.setBackground(new Color(38, 38, 38));
        accField.setForeground(new Color(102, 255, 153));
        accField.setCaretColor(Color.WHITE);
        accField.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1));
        formCard.add(accField, fg);

        // Row 2: Masked PIN Field
        fg.gridx = 0;
        fg.gridy = 2;
        JLabel pinLbl = new JLabel("ENTER 4-DIGIT PIN");
        pinLbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        pinLbl.setForeground(Color.WHITE);
        formCard.add(pinLbl, fg);

        fg.gridx = 1;
        pinDisplayField = new JPasswordField();
        pinDisplayField.setPreferredSize(new Dimension(280, 46));
        pinDisplayField.setEditable(false);
        pinDisplayField.setHorizontalAlignment(SwingConstants.CENTER);
        pinDisplayField.setFont(new Font("Monospaced", Font.BOLD, 24));
        pinDisplayField.setBackground(new Color(38, 38, 38));
        pinDisplayField.setForeground(new Color(255, 204, 51));
        pinDisplayField.setEchoChar('●');
        pinDisplayField.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 51), 2));
        formCard.add(pinDisplayField, fg);

        // Row 3: Status / Feedback Message
        fg.gridx = 0;
        fg.gridy = 3;
        fg.gridwidth = 2;
        fg.insets = new Insets(12, 5, 12, 5);
        feedbackLabel = new JLabel("Tap your PIN on the keypad (Demo PIN: 1234)", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        feedbackLabel.setForeground(new Color(190, 190, 190));
        formCard.add(feedbackLabel, fg);

        // Row 4: Cancel / Eject Card Button
        fg.gridy = 4;
        fg.insets = new Insets(8, 8, 0, 8);
        JButton cancelBtn = new JButton("CANCEL / EJECT CARD");
        cancelBtn.setPreferredSize(new Dimension(280, 44));
        cancelBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        cancelBtn.setBackground(new Color(185, 45, 45));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelBtn.addActionListener(e -> {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this, "Card ejected. Thank you for banking with Bintang Bank.");
            this.dispose();
            new HOME().setVisible(true);
        });
        formCard.add(cancelBtn, fg);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        mainBody.add(formCard, gbc);

        // --- RIGHT TACTILE ATM KEYPAD ---
        JPanel keypadPanel = new JPanel(new GridLayout(4, 3, 12, 12));
        keypadPanel.setBackground(new Color(26, 26, 26));
        keypadPanel.setPreferredSize(new Dimension(330, 320));
        keypadPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(65, 65, 65), 1),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));

        String[] keys = {
            "1", "2", "3",
            "4", "5", "6",
            "7", "8", "9",
            "CLEAR", "0", "ENTER"
        };

        keypadButtons = new JButton[keys.length];
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            JButton btn = new JButton(key);
            btn.setFont(new Font("SansSerif", Font.BOLD, 16));
            btn.setFocusPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            if ("CLEAR".equals(key)) {
                btn.setBackground(new Color(255, 204, 51));
                btn.setForeground(Color.BLACK);
            } else if ("ENTER".equals(key)) {
                btn.setBackground(new Color(46, 160, 67));
                btn.setForeground(Color.WHITE);
            } else {
                btn.setBackground(new Color(55, 55, 55));
                btn.setForeground(Color.WHITE);
            }

            Color defaultBg = btn.getBackground();
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (btn.isEnabled()) {
                        btn.setBackground(defaultBg.brighter());
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (btn.isEnabled()) {
                        btn.setBackground(defaultBg);
                    }
                }
            });

            btn.addActionListener(e -> handleKeyPress(key));
            keypadButtons[i] = btn;
            keypadPanel.add(btn);
        }

        gbc.gridx = 1;
        gbc.weightx = 0.4;
        mainBody.add(keypadPanel, gbc);

        getContentPane().add(mainBody, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    private void handleKeyPress(String key) {
        Toolkit.getDefaultToolkit().beep();
        String currentPin = new String(pinDisplayField.getPassword());

        if ("CLEAR".equals(key)) {
            pinDisplayField.setText("");
            feedbackLabel.setForeground(new Color(190, 190, 190));
            feedbackLabel.setText("PIN cleared. Enter 4-digit PIN.");
        } else if ("ENTER".equals(key)) {
            verifyLogin();
        } else {
            if (currentPin.length() < 6) {
                pinDisplayField.setText(currentPin + key);
            }
        }
    }

    private void verifyLogin() {
        String accNo = accField.getText().trim();
        String enteredPin = new String(pinDisplayField.getPassword());

        if (accNo.isEmpty()) {
            feedbackLabel.setForeground(new Color(255, 102, 102));
            feedbackLabel.setText("Please enter your Account Number!");
            return;
        }

        if (enteredPin.isEmpty()) {
            feedbackLabel.setForeground(new Color(255, 102, 102));
            feedbackLabel.setText("Please enter your PIN using the keypad!");
            return;
        }

        if (VALID_PIN.equals(enteredPin)) {
            feedbackLabel.setForeground(new Color(102, 255, 153));
            feedbackLabel.setText("PIN Verified! Loading your account menu...");
            setKeypadEnabled(false);

            Timer t = new Timer(800, e -> {
                this.dispose();
                new Menu().setVisible(true);
            });
            t.setRepeats(false);
            t.start();
        } else {
            failedAttempts++;
            pinDisplayField.setText("");
            int remaining = MAX_ATTEMPTS - failedAttempts;

            if (remaining > 0) {
                feedbackLabel.setForeground(new Color(255, 102, 102));
                feedbackLabel.setText("Incorrect PIN! " + remaining + " attempt(s) remaining.");
            } else {
                setKeypadEnabled(false);
                feedbackLabel.setForeground(Color.RED);
                feedbackLabel.setText("CARD LOCKED due to 3 failed attempts!");
                JOptionPane.showMessageDialog(this,
                        "Security Alert: 3 invalid PIN attempts.\nYour card has been retained for security.",
                        "Card Locked",
                        JOptionPane.ERROR_MESSAGE);
                this.dispose();
                new HOME().setVisible(true);
            }
        }
    }

    private void setKeypadEnabled(boolean enabled) {
        if (keypadButtons != null) {
            for (JButton b : keypadButtons) {
                b.setEnabled(enabled);
            }
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jTextField3 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("LOGIN");

        jLabel3.setText("ACC NUM");

        jLabel4.setText("PIN CODE");

        jButton11.setText("LOGIN");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 861, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel5.setFont(new java.awt.Font("Engravers MT", 2, 48)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 204, 51));
        jLabel5.setText("BINTANG BANK");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(327, 327, 327)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel5)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(257, 257, 257)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(124, 124, 124)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(514, 514, 514)
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(jLabel1))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(547, 547, 547)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(314, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(68, 68, 68))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        verifyLogin();
        
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        verifyLogin();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

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
            java.util.logging.Logger.getLogger(Next.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new Next().setVisible(true));
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton11;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}

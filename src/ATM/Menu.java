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
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class Menu extends javax.swing.JFrame {
    
    String accNO = "8801-4492-1029";
    public static double BALANCE = 1000.00;
    public static List<String> TRANSACTION_HISTORY = new ArrayList<>();

    private JLabel sessionTimerLabel;
    private JLabel quickBalLabel;
    private Timer inactivityTimer;
    private int secondsRemaining = 60;

    public Menu() {
        initComponents();
        buildCleanMenuInterface();
    }

    private void buildCleanMenuInterface() {
        setTitle("Bintang Bank - Main Transaction Menu");
        setResizable(false);

        getContentPane().removeAll();
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(18, 18, 18));

        // 1. TOP HEADER PANEL
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.BLACK);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(255, 204, 51)),
                BorderFactory.createEmptyBorder(15, 25, 18, 25)
        ));

        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setBackground(Color.BLACK);

        JLabel titleLbl = new JLabel("BINTANG BANK", SwingConstants.CENTER);
        titleLbl.setFont(new Font("Engravers MT", Font.ITALIC, 44));
        titleLbl.setForeground(new Color(255, 204, 51));
        topRow.add(titleLbl, BorderLayout.CENTER);

        sessionTimerLabel = new JLabel("Auto-Eject: 60s", SwingConstants.RIGHT);
        sessionTimerLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        sessionTimerLabel.setForeground(new Color(255, 204, 51));
        topRow.add(sessionTimerLabel, BorderLayout.EAST);

        JPanel subHeader = new JPanel(new GridLayout(2, 1, 0, 6));
        subHeader.setBackground(Color.BLACK);
        subHeader.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        JLabel promptLbl = new JLabel("SELECT A TRANSACTION", SwingConstants.CENTER);
        promptLbl.setFont(new Font("SansSerif", Font.BOLD, 18));
        promptLbl.setForeground(Color.WHITE);

        JLabel accStatusLbl = new JLabel("ACCOUNT: 8801-XXXX-1029   •   ACTIVE SESSION", SwingConstants.CENTER);
        accStatusLbl.setFont(new Font("Monospaced", Font.BOLD, 14));
        accStatusLbl.setForeground(new Color(102, 255, 153));

        subHeader.add(promptLbl);
        subHeader.add(accStatusLbl);

        headerPanel.add(topRow, BorderLayout.CENTER);
        headerPanel.add(subHeader, BorderLayout.SOUTH);
        getContentPane().add(headerPanel, BorderLayout.NORTH);

        // 2. CENTER BODY PANEL
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(18, 18, 18));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(25, 60, 35, 60));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 25, 0);

        DecimalFormat df = new DecimalFormat("#,##0.00");
        quickBalLabel = new JLabel("AVAILABLE BALANCE:   RM " + df.format(BALANCE), SwingConstants.CENTER);
        quickBalLabel.setOpaque(true);
        quickBalLabel.setBackground(new Color(28, 28, 28));
        quickBalLabel.setForeground(new Color(255, 204, 51));
        quickBalLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
        quickBalLabel.setPreferredSize(new Dimension(560, 50));
        quickBalLabel.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 51), 1));
        centerPanel.add(quickBalLabel, gbc);

        // 2 Rows x 3 Columns Button Grid (Zero Overlapping!)
        JPanel buttonGrid = new JPanel(new GridLayout(2, 3, 24, 22));
        buttonGrid.setBackground(new Color(18, 18, 18));
        buttonGrid.setPreferredSize(new Dimension(920, 165));

        styleMenuButton(jButton1, "WITHDRAW CASH", new Color(40, 40, 40), new Color(255, 204, 51));

        JButton fastCashBtn = new JButton("FAST CASH (RM 100)");
        styleMenuButton(fastCashBtn, "FAST CASH (RM 100)", new Color(46, 160, 67), Color.WHITE);
        fastCashBtn.addActionListener(e -> handleFastCash100());

        styleMenuButton(jButton2, "BALANCE INQUIRY", new Color(40, 40, 40), new Color(255, 204, 51));
        styleMenuButton(jButton3, "DEPOSIT CASH", new Color(40, 40, 40), new Color(255, 204, 51));

        JButton historyBtn = new JButton("MINI STATEMENT");
        styleMenuButton(historyBtn, "MINI STATEMENT", new Color(40, 40, 40), new Color(102, 255, 153));
        historyBtn.addActionListener(e -> showMiniStatement());

        EXIT.setText("EJECT CARD / EXIT");
        EXIT.setFont(new Font("SansSerif", Font.BOLD, 16));
        EXIT.setBackground(new Color(185, 45, 45));
        EXIT.setForeground(Color.WHITE);
        EXIT.setFocusPainted(false);
        EXIT.setCursor(new Cursor(Cursor.HAND_CURSOR));
        EXIT.setBorder(BorderFactory.createLineBorder(new Color(255, 120, 120), 1));

        buttonGrid.add(jButton1);
        buttonGrid.add(fastCashBtn);
        buttonGrid.add(jButton2);
        buttonGrid.add(jButton3);
        buttonGrid.add(historyBtn);
        buttonGrid.add(EXIT);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        centerPanel.add(buttonGrid, gbc);

        getContentPane().add(centerPanel, BorderLayout.CENTER);
        startInactivityTimer();
        pack();
        setLocationRelativeTo(null);
    }

    private void styleMenuButton(JButton btn, String text, Color bg, Color fg) {
        btn.setText(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(new Color(255, 204, 51), 1));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                secondsRemaining = 60;
                btn.setBackground(bg.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bg);
            }
        });
    }

    private void startInactivityTimer() {
        inactivityTimer = new Timer(1000, e -> {
            secondsRemaining--;
            sessionTimerLabel.setText("Auto-Eject: " + secondsRemaining + "s");
            if (secondsRemaining <= 10) {
                sessionTimerLabel.setForeground(new Color(255, 102, 102));
            }
            if (secondsRemaining <= 0) {
                inactivityTimer.stop();
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(this, "Session timed out for your security. Card ejected.");
                this.dispose();
                new HOME().setVisible(true);
            }
        });
        inactivityTimer.start();
    }

    public static void logTransaction(String type, double amount) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM HH:mm:ss"));
        TRANSACTION_HISTORY.add(String.format("[%s] %-14s RM %10s  (Bal: RM %s)",
                time, type, df.format(amount), df.format(BALANCE)));
    }

    private void handleFastCash100() {
        secondsRemaining = 60;
        Toolkit.getDefaultToolkit().beep();
        if (BALANCE < 100) {
            JOptionPane.showMessageDialog(this, "Insufficient balance for RM 100.00 Fast Cash!", "Insufficient Funds", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Dispense RM 100.00 Fast Cash immediately?", "Fast Cash", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            BALANCE -= 100;
            logTransaction("FAST CASH", 100);
            DecimalFormat df = new DecimalFormat("#,##0.00");
            quickBalLabel.setText("AVAILABLE BALANCE:   RM " + df.format(BALANCE));
            JOptionPane.showMessageDialog(this, "Please take your cash: RM 100.00 (1 x RM 100 note)\nRemaining Balance: RM " + df.format(BALANCE));
        }
    }

    private void showMiniStatement() {
        secondsRemaining = 60;
        Toolkit.getDefaultToolkit().beep();
        DecimalFormat df = new DecimalFormat("#,##0.00");
        StringBuilder sb = new StringBuilder();
        sb.append("=================================================\n");
        sb.append("         BINTANG BANK - MINI STATEMENT           \n");
        sb.append("=================================================\n");
        if (TRANSACTION_HISTORY.isEmpty()) {
            sb.append("  No transactions recorded in this session yet.\n");
        } else {
            for (String entry : TRANSACTION_HISTORY) {
                sb.append(" ").append(entry).append("\n");
            }
        }
        sb.append("-------------------------------------------------\n");
        sb.append(" CURRENT BALANCE: RM ").append(df.format(BALANCE)).append("\n");
        sb.append("=================================================");

        JTextArea area = new JTextArea(sb.toString());
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));
        area.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Mini Statement", JOptionPane.INFORMATION_MESSAGE);
    }

    private void stopTimerAndDispose() {
        if (inactivityTimer != null) {
            inactivityTimer.stop();
        }
        this.dispose();
    }
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        label1 = new java.awt.Label();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        EXIT = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel2.setFont(new java.awt.Font("Engravers MT", 2, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 204, 51));
        jLabel2.setText("BINTANG BANK");

        jLabel3.setFont(new java.awt.Font("Sylfaen", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));

        label1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        label1.setForeground(new java.awt.Color(255, 255, 255));
        label1.setText("Please Select a transaction");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(551, 551, 551)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(433, 433, 433)
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(314, 314, 314)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(329, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(26, 26, 26)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setBackground(new java.awt.Color(153, 153, 153));
        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jButton1.setText("WITHDRAW");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(153, 153, 153));
        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jButton2.setText("BALANCE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(153, 153, 153));
        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        jButton3.setText("DEPOSIT");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        EXIT.setBackground(new java.awt.Color(153, 153, 153));
        EXIT.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        EXIT.setText("KELUAR");
        EXIT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EXITActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(119, 119, 119)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(EXIT, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(303, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EXIT, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(126, 126, 126))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Toolkit.getDefaultToolkit().beep();
        stopTimerAndDispose();
        new BALANCE().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Toolkit.getDefaultToolkit().beep();
        stopTimerAndDispose();
        new WITHDRAW().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Toolkit.getDefaultToolkit().beep();
        stopTimerAndDispose();
        new DEPOSIT().setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void EXITActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EXITActionPerformed
        Toolkit.getDefaultToolkit().beep();
        if (JOptionPane.showConfirmDialog(this, "Eject your card and end session?", "Eject Card",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            stopTimerAndDispose();
            new HOME().setVisible(true);
        }
    }//GEN-LAST:event_EXITActionPerformed

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
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new Menu().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton EXIT;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private java.awt.Label label1;
    // End of variables declaration//GEN-END:variables
}

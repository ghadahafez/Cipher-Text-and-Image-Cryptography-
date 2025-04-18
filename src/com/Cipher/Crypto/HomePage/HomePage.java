package com.Cipher.Crypto.HomePage;

import com.Cipher.Crypto.AlgorithmView.EncryptionDecryption.ImageEncryptionDecryption.ImageEncryptionDecryption;
import com.Cipher.Crypto.AlgorithmView.EncryptionDecryption.TextEncryptionDecryption.TextEncryptionDecryption;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class HomePage extends JFrame {

    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JLabel label1;
    
    public HomePage() {
        initComponents();

        Frame frame = new Frame();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/icon.png")));
        this.setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        initialize();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cryptography");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        button1.setFont(new Font("Tahoma", 1, 14));
        button1.setText("Text Encryption and Decryption");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button1ActionPerformed(e);
            }
        });
        getContentPane().add(button1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 340, 240, 60));

        button3.setFont(new Font("Tahoma", 1, 14));
        button3.setText("Image Encryption and Decryption");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button3ActionPerformed(e);
            }
        });
        getContentPane().add(button3, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 340, 260, 60));

        button2.setBackground(new Color(5, 116, 232));
        button2.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/help.png"))));
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button2ActionPerformed(e);
            }
        });
        getContentPane().add(button2, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 0, -1, -1));

        label1.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/HomeUI.jpg"))));
        getContentPane().add(label1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }

    private void button2ActionPerformed(ActionEvent e) {
        String message = "<html><body width='350'>" +
                "<h3>Cipher Encryption and Decryption Software</h3>" +
                "<p>This project is developed using <b>Java</b> (via IntelliJ IDE).</p>" +
                "<p>The aim of this system is to create a user-friendly GUI that helps users to <b>Encrypt and Decrypt</b> messages and images.</p>" +
                "<br><hr><div style='text-align:right; font-size:10px;'>MadeBy@ShreeGovindJee</div>" +
                "</body></html>";
        JOptionPane.showMessageDialog(null, message, "About", JOptionPane.INFORMATION_MESSAGE);
    }

    private void button3ActionPerformed(ActionEvent e) {
        new ImageEncryptionDecryption().setVisible(true);
    }


    private void button1ActionPerformed(ActionEvent e) {
        new TextEncryptionDecryption().setVisible(true);
    }


    private void initialize() {
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();

        label1 = new JLabel();
    }
}

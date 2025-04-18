package com.Cipher.Crypto.AlgorithmView.EncryptionDecryption.TextEncryptionDecryption;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import javax.crypto.Cipher;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Objects;

public class TextEncryptionDecryption extends JFrame {

    private Component JFrameComponent;
    private JTextArea input_textArea;

    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;

    private JLabel jLabel;
    private JScrollPane scrollPane;

    private PublicKey publicKey;
    private PrivateKey privateKey;
    private void generateKeyPair() {
        boolean flag = true;
        System.out.println("[SecretKeyPairGeneration] Generating .....");
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            this.publicKey = keyPair.getPublic();
            this.privateKey = keyPair.getPrivate();
        } catch (Exception exception) {
            flag = false;
            System.err.println("[SecretKeyPairGeneration] Error Encountered while Generating the SecretKey --> "+exception.toString());
        } finally {
            if (flag) {
                System.out.println("[SecretKeyPairGeneration] Successfully KeyPair Generated\n");
            }
        }
    }

    public TextEncryptionDecryption() {
        initComponents();

        this.setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/icon.png")));

//        GENERATE KEYPAIR
        generateKeyPair();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        initialize();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cipher Texting");
        setResizable(false);
        getContentPane().setLayout(new AbsoluteLayout());

        input_textArea.setText("Press The \"Clear\" Button below.\nAnd Start Entering Your Message That need to be Encrypted or Decrypted....");
        input_textArea.setRows(5);
        getContentPane().add(input_textArea, new AbsoluteConstraints(80, 100, 500, 230));
        getContentPane().add(scrollPane, new AbsoluteConstraints(270, 220, -1, -1));


        button1.setText("Encrypt");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button1ActionPerformed(e);
            }
        });
        getContentPane().add(button1, new AbsoluteConstraints(30, 360, 110, 40));

        button2.setText("Decrypt");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button2ActionPerformed(e);
            }
        });
        getContentPane().add(button2, new AbsoluteConstraints(170, 360, 110, 40));

        button3.setText("Clear");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button3ActionPerformed(e);
            }
        });
        getContentPane().add(button3, new AbsoluteConstraints(320, 360, 110, 40));

        button4.setText("Copy To ClipBoard");
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button4ActionPerformed(e);
            }
        });
        getContentPane().add(button4, new AbsoluteConstraints(460, 360, 190, 40));

        button5.setBackground(new Color(5, 116, 232));
        button5.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/backArrow.png"))));
        button5.setOpaque(false);
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button5ActionPerformed(e);
            }
        });
        getContentPane().add(button5, new AbsoluteConstraints(0, 0, 70, 70));

        button6.setBackground(new Color(5, 116, 232));
        button6.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/help.png"))));
        button6.setOpaque(false);
        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button6ActionPerformed(e);
            }
        });
        getContentPane().add(button6, new AbsoluteConstraints(0, 70, 70, 60));

        jLabel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/textecy.jpg"))));
        getContentPane().add(jLabel, new AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }


    private void button6ActionPerformed(ActionEvent e) {
        String message = "<html><body width='350'>" +
                "<h3>Cipher Encryption and Decryption Software</h3>" +
                "<p>This project is developed using <b>Java</b> (via IntelliJ IDE).</p>" +
                "<p>The aim of this system is to create a user-friendly GUI that helps users to <b>Encrypt and Decrypt</b> messages and images.</p>" +
                "<br><hr><div style='text-align:right; font-size:10px;'>MadeBy @ShreeGovindJee</div>" +
                "</body></html>";
        JOptionPane.showMessageDialog(null, message, "About", JOptionPane.INFORMATION_MESSAGE);
    }

    private void button5ActionPerformed(ActionEvent e) {
        this.toBack();
    }

    private void button4ActionPerformed(ActionEvent e) {
        StringSelection stringSelection = new StringSelection(input_textArea.getText());

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        JOptionPane.showMessageDialog(JFrameComponent, "Your Message is Copied.");
    }

    private void button3ActionPerformed(ActionEvent e) {
        input_textArea.setText("");
    }

    private void button2ActionPerformed(ActionEvent e) {
        System.out.println("[TextEncryptionDecryption] Reading the user Message....");
        String userMessage = input_textArea.getText();
        System.out.println("[TextEncryptionDecryption] User Message Read Successfully....");

        System.out.println("[TextEncryptionDecryption] Decrypting the user Message....");
        input_textArea.setText(decryptMessage(userMessage));
        System.out.println("[TextEncryptionDecryption] Decrypting the user Message Successfully....\n");
    }

    private void button1ActionPerformed(ActionEvent e) {
        System.out.println("[TextEncryptionDecryption] Reading the user Message....");
        String userMessage = input_textArea.getText();
        System.out.println("[TextEncryptionDecryption] User Message Read Successfully....");

        System.out.println("[TextEncryptionDecryption] Encrypting the user Message....");
        input_textArea.setText(encryptKey(userMessage));
        System.out.println("[TextEncryptionDecryption] Encrypting the user Message Successfully....\n");

        Component FrameComponent = null;
    }

    private @Nullable String encryptKey(String userMessage) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] encryptedMessage = cipher.doFinal(userMessage.getBytes());
            return Base64.getEncoder().encodeToString(encryptedMessage);
        } catch (Exception exception) {
            System.out.println("[TextEncryptionDecryption] Error Encountered while Encrypting the Data --> " +exception.getMessage());
        }

        return null;
    }

    @Contract(pure = true)
    private @Nullable String decryptMessage(String userMessage) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] decryptedMessage = Base64.getDecoder().decode(userMessage);
            return new String(cipher.doFinal(decryptedMessage));
        } catch (Exception exception) {
            System.out.println("[TextEncryptionDecryption] Error Encountered while Decrypting the Data --> " +exception.getMessage());
        }

        return null;
    }

    private void initialize() {
        input_textArea = new JTextArea();
        scrollPane = new JScrollPane();

        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        button4 = new JButton();
        button5 = new JButton();
        button6 = new JButton();

        jLabel = new JLabel();
    }
}

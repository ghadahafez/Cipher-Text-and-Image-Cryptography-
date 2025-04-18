package com.Cipher.Crypto.AlgorithmView.EncryptionDecryption.ImageEncryptionDecryption;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;


public class ImageEncryptionDecryption extends JFrame implements ActionListener {

    private final long SERIAL_VERSION_UID = 1L;

    private ImageRead imageReadPanel;
    private ImageEncrypt imageEncrypt;
    private File file;

    public ImageEncryptionDecryption() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Image Cryptography");
        setResizable(false);
        getContentPane().setLayout(new AbsoluteLayout());

        this.setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/icon.png")));

        imageReadPanel = new ImageRead();
        getContentPane().add(imageReadPanel, new AbsoluteConstraints(0, 0, 880, 550));
        pack();

        setJMenuBar(MainMenu());
        this.setLocationRelativeTo(null);

        imageEncrypt = new ImageEncrypt();
    }

    private @NotNull JMenuBar MainMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu file, menu, help;
        JMenuItem open, save, saveas, close, setKey, Encrypt, Decrypt, about, back;

        file = new JMenu("File");
        menu = new JMenu("CipherOperation");
        help = new JMenu("Help");

        open = new JMenuItem("Open", loadIcon("/assets/images/folder.png", 20, 20));
        save = new JMenuItem("Save", loadIcon("/assets/images/save.png", 20, 20));
        saveas = new JMenuItem("Save As", loadIcon("/assets/Images/saveas.png", 20, 20));
        close = new JMenuItem("Close", loadIcon("/assets/images/close.png", 20, 20));
        setKey = new JMenuItem("Set Pass Key", loadIcon("/assets/images/key.png", 20, 20));
        Encrypt = new JMenuItem("Encrypt Image", loadIcon("/assets/images/lock.png", 20, 20));
        Decrypt = new JMenuItem("Decrypt Image", loadIcon("/assets/images/unlock.png", 20, 20));
        about = new JMenuItem("About", loadIcon("/assets/images/info.png", 20, 20));
        back = new JMenuItem("Back", loadIcon("/assets/images/back.png", 20, 20));

        file.add(open);
        file.addSeparator();
        file.add(save);
        file.add(saveas);
        file.addSeparator();
        file.add(close);
        menu.add(setKey);
        menu.addSeparator();
        menu.add(Encrypt);
        menu.add(Decrypt);
        help.add(back);
        help.add(about);

        menuBar.add(file);
        menuBar.add(menu);
        menuBar.add(help);

        open.addActionListener(this);
        setKey.addActionListener(this);
        close.addActionListener(this);
        save.addActionListener(this);
        Encrypt.addActionListener(this);
        about.addActionListener(this);
        back.addActionListener(this);
        saveas.addActionListener(this);
        Decrypt.addActionListener(this);

        return menuBar;
    }

    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        String text = e.getActionCommand();
        try {
            switch (text) {
                case "Open" -> actionLoadImage(null);
                case "Save" -> actionSaveImage(file);
                case "Save As" -> actionSaveImage(null);
                case "Close" -> System.exit(0);
                case "Set Pass Key" -> actionKeyDialog(file);
                case "Encrypt Image" -> imageReadPanel.setImage(imageEncrypt.map(imageReadPanel.getImage(), true, false));
                case "Decrypt Image" -> imageReadPanel.setImage(imageEncrypt.map(imageReadPanel.getImage(), false, false));
                case "About" -> displayContactInfo();
                case "Back" -> this.toBack();
            }
        } catch(Exception exception) {
            System.out.println("[ImageED] Error occurred while \"ActionPerformed()\"");
            exception.printStackTrace();
        }
    }

    private void displayContactInfo() {
        String infoMessage = "<html><body style='width: 350px;'>"
                + "This program encrypts image files using 128-bit AES "
                + "(Advanced Encryption Standard) algorithm.<br><br>"
                + "It can easily be converted to support:<br>"
                + " - AES 192-bit<br>"
                + " - AES 256-bit<br>"
                + " - DES 56-bit encryption<br><br>"
                + "This uses the Java Cryptographic Extension (JCE) API "
                + "from the <code>javax.crypto</code> package."
                + "</body></html>";
        JOptionPane.showMessageDialog(null, infoMessage, "Contact Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void actionKeyDialog(File file) {
        String key = new String(imageEncrypt.getKey());
        key = (String) JOptionPane.showInputDialog(this, "Enter a 16 Byte key [CurrentKey = "+key.getBytes().length+" bytes]", key);

        while (key != null && key.getBytes().length != 16) {
            key = (String) JOptionPane.showInputDialog(this, "Enter a 16 Byte key [CurrentKey = "+key.getBytes().length+" bytes]", key);
        }
        if (key != null) {
            imageEncrypt.setKey(key.getBytes());
        }
    }

    private void actionSaveImage(File file) {
        if (file == null) {
            JFileChooser fileChooser = new JFileChooser(file);
            fileChooser.showSaveDialog(this);
            file = fileChooser.getSelectedFile();
        }

        if (file != null) {
            try {
                ImageIO.write(imageReadPanel.getImage(), "png", file);
            } catch (Exception exception) {
                System.out.println("[ActionSaveImage --> ImageBufferedFile] Error occurred while saving the Image file...");
                exception.printStackTrace();
            }
            setFile(file);
        }
    }

    private void actionLoadImage(File imageFile) {
        if (imageFile == null) {
            JFileChooser fileChooser = new JFileChooser(file);
            fileChooser.setControlButtonsAreShown(false);
            fileChooser.showOpenDialog(this);

            imageFile = fileChooser.getSelectedFile();
        }
        if (imageFile != null) {
            imageReadPanel.setImage(imageFromFile(imageFile));
            setFile(imageFile);
        }
    }

    private BufferedImage imageFromFile(File file) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file);
        } catch (Exception exception) {
            System.out.println("[ImageFromFile --> BufferedImaged Reader] Error occurred while reading the image form the file into BufferedFormat");
            exception.printStackTrace();
        }
        return bufferedImage;
    }

    public void setFile(File file) {
        this.file = file;
    }

    private @Nullable ImageIcon loadIcon(String path, int width, int height) {
        URL iconURL = getClass().getResource(path);
        if (iconURL != null) {
            ImageIcon imageIcon = new ImageIcon(iconURL);
            Image scaledImage = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } else {
            System.err.println("[ImageIcon Scaled] Error in Icon Path --> "+path);
        }
        return null;
    }
}


/*
class ImageRead extends JPanel{
    private BufferedImage image;

    public ImageRead() {
        this.image = null;

        setFocusable(true);
        setLayout(null);
        setOpaque(true);
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        repaint();
    }

    public BufferedImage getImage() {
        return image;
    }

    public void paintComponent(@NotNull Graphics graphics) {
        graphics.setColor(new Color(34, 33, 33));
        graphics.fillRect(0, 0, getSize().width, getSize().height);
        if (image != null) {
            int X_CENTER = getSize().width / 2 - image.getWidth() / 2;
            int Y_CENTER = getSize().height / 2 - image.getHeight() / 2;
            if (X_CENTER < 10) {
                X_CENTER = 10;
            }
            if (Y_CENTER < 10) {
                Y_CENTER = 10;
            }

            graphics.drawImage(image, X_CENTER, Y_CENTER, null);
        }
    }
}
 */

/*
class ImageEncrypt {
    private boolean bool = false;
    private Cipher cipher;
    private SecretKeySpec secretKeySpec;

    public ImageEncrypt() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);

            SecretKey secretKey = keyGenerator.generateKey();
            secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");

            cipher = Cipher.getInstance("AES/ECB/NoPadding");
        } catch (Exception exception) {
            System.out.println("[ImageEncryption] Error Occurred while Key Generation...");
            exception.printStackTrace();
        }
    }

    public void setKey(byte[] keyByte) {
        secretKeySpec = new SecretKeySpec(keyByte, "AES");
    }

    public byte[] getKey() {
        return secretKeySpec.getEncoded();
    }


    public BufferedImage map(BufferedImage bufferedImage, boolean encrypt, boolean trick) {
        try {
            if (bufferedImage.getWidth()%2 != 0 || bufferedImage.getHeight()%2 != 0) {
                throw new Exception("[ImageEncrypt] Image Size not multiple of 2...");
            }

            BufferedImage encryptImageBuffered = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            if (encrypt) {
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            }

            for (int i = 0; i < bufferedImage.getWidth(); i+=2) {
                for (int j = 0; j < bufferedImage.getHeight(); j+=2) {
                    if (bool) {
                        System.out.println("[ImageEncrypt] Block --> (" + i + ", " + j + ")");
                    }

                    int counter = 0;
                    byte[] pixel = new byte[16];
                    for (int idx = 0; idx < 2; idx++) {
                        for (int jdx = 0; jdx < 2; jdx++) {
                            int val = bufferedImage.getRGB(i + idx, j + jdx);
                            if (trick && encrypt) {
                                val += i * j;
                            }

                            byte[] sub = intToByteArray(val);
                            if (bool) {
                                printByteArray(sub);
                            }

                            for (int k = 0; k < 4; k++){
                                pixel[counter * 4 + k] = sub[k];
                            }
                            counter++;
                        }
                    }

                    byte[] encryptByte = cipher.doFinal(pixel);
                    if (bool) {
                        printByteArray(pixel);
                        printByteArray(encryptByte);
                    }
                    counter = 0;
                    for (int i1 = 0; i1 < 2; i1++){
                        for (int j1 = 0; j1 < 2; j1++){
                            byte[] sub = new byte[4];
                            for (int k = 0; k < 4; k++){
                                sub[k] = encryptByte[(counter) * 4 + k];
                            }

                            int val = byteArrayToInt(sub);
                            if (trick && !encrypt) {
                                val -= i*j;
                            }
                            encryptImageBuffered.setRGB(i + i1, j + j1, val);
                            counter++;
                        }
                    }
                }
            }
            return encryptImageBuffered;
        } catch (Exception exception) {
            System.out.println("[EncryptionDecryption Error] Error Encountered while Image Encryption and Decryption...");
            exception.printStackTrace();
        }

        return null;
    }

    @Contract(pure = true)
    private int byteArrayToInt(byte @NotNull [] sub) {
        return (sub[0] << 24) + ((sub[1] & 0xFF) << 16) + ((sub[2] & 0xFF) << 8) + (sub[3] & 0xFF);
    }

    private void printByteArray(byte @NotNull [] sub) {
        System.out.print("{");
        for (byte s : sub) {
            System.out.print(" "+s);
        }
        System.out.println("}");
    }

    @Contract(value = "_ -> new", pure = true)
    private byte @NotNull [] intToByteArray(int val) {
        return new byte[] {(byte) (val >> 24), (byte) (val >> 16), (byte) (val >> 8), (byte) val};
    }
}
 */
package com.Cipher.Crypto.AlgorithmView.EncryptionDecryption.ImageEncryptionDecryption;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class ImageRead extends JPanel {
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


    @Override
    protected void paintComponent(@NotNull Graphics graphics) {
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

package com.Cipher.Crypto;

import com.Cipher.Crypto.HomePage.HomePage;

import java.awt.*;

public class MainApplication {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HomePage().setVisible(true);
            }
        });
    }
}

package com.Cipher.Crypto.AlgorithmView.EncryptionDecryption.ImageEncryptionDecryption;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.awt.image.BufferedImage;

public class ImageEncrypt {
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

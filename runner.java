package com.iss5.encryption.rsa;

public class runner {

    public static void main(String[] args) {

        RSA rsa = new RSA();
        System.out.println("---KEY-Values---");
        rsa.printKeys();
        System.out.println();
        System.out.println("---Encryption---");

        String encrypted = rsa.EncryptText("markfrequency");
        System.out.println("Returned encrypted value: " + encrypted);

        //System.out.println("Returned decrypted value: " + rsa.decryptBlock(encrypted));
    }
}

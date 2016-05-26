package com.iss5.encryption.rsa;

import com.iss5.encryption.IEncryptionRunner;
import java.math.BigInteger;

public class RSA implements IEncryptionRunner {

    private Key _publicKey;
    private Key _privateKey;

    public RSA() {
        _publicKey = new Key();
        _privateKey = new Key();
        // Generate the 2 keys
        _publicKey = _publicKey.generateKey("public");
        _privateKey = _privateKey.generateKey("private");
    }

    public String Run (String plaintext) {

        RSA rsa = new RSA();
        return rsa.EncryptText(plaintext);
    }

    // Output Keys
    public void printKeys() {
        System.out.println("Public Key is: " + _publicKey.keyValue());
        System.out.println("Private Key is: " + _privateKey.keyValue());

    }

    /**
     * Method splits plainText into blocks and calls encryption on each block,
     * appends each block to cipher text.
     *
     * To be used in client server to allow users to have passwords containing
     * special characters.
     *
     * @param plainText - The text to encrypt
     */
    public String EncryptTextASCII(String plainText) {

        String value = "";
        String plainTextBlock = "";
        char first, second;

        for (int i = 0; i < plainText.length(); i+=2) {

            if(i == plainText.length() - 1) {
                first = plainText.charAt(i);

                // Encrypt block, append to value and reset
                // variable for next round
                int ascii = (int)first;
                System.out.println(ascii);
                plainTextBlock += ascii;
                value += encryptBlock(plainTextBlock);
                plainTextBlock = "";
            }
            else {
                first = plainText.charAt(i);
                second = plainText.charAt(i + 1);

                int ascii = (int)first;
                plainTextBlock += ascii;

                ascii = (int)second;
                plainTextBlock += ascii;

                value += encryptBlock(plainTextBlock);
                plainTextBlock = "";
            }
        }

        return value;
    }

    /**
     * Method splits plainText into blocks and calls encryption on each block,
     * appends each block to cipher text.
     *
     * To be used in console application for assignment.
     *
     * @param plainText - The text to encrypt
     */
    public String EncryptText(String plainText) {

        String value = "";
        String plainTextBlock = "";
        char first, second;

        for (int i = 0; i < plainText.length(); i+=2) {

            if(i == plainText.length() - 1) {
                first = plainText.charAt(i);
                // Check valid char
                if (first >= 'a' && first <= 'z') {
                    plainTextBlock += (int) first - 'a' + 1;

                    // Encrypt block, append to value and reset
                    // variable for next round
                    value += encryptBlock(plainTextBlock);
                    plainTextBlock = "";
                }
            }
            else {
                first = plainText.charAt(i);
                second = plainText.charAt(i + 1);

                // Check valid char
                if (first >= 'a' && first <= 'z'
                        && second >= 'a' && second <= 'z') {

                    plainTextBlock += (int) first - 'a' + 1;

                    if (second - 'a' + 1 < 10) {
                        plainTextBlock += 0;
                        plainTextBlock += second - 'a' + 1;
                    }
                    else {
                        plainTextBlock += (int) second - 'a' + 1;
                    }

                    // Encrypt block, append to value and reset
                    // variable for next round
                    value += encryptBlock(plainTextBlock);
                    plainTextBlock = "";
                }
            }
        }
        return value;
    }

    /**
     * Method accepts blocks of plainText and returns the encryption.
     *
     * @param plainTextBlock - Block from the plainText to be encrypted
     * @return Encrypted block to be appended to cipher text
     */
    private String encryptBlock(String plainTextBlock) {

        // To encrypt:
        // C = M^e(mod n)

        // Get value of E and convert to binary
        String binary = Integer.toBinaryString(_publicKey.get_e());
        BigInteger m = BigInteger.valueOf(Integer.parseInt(plainTextBlock));

        // Get value of N to be used in modulus expression
        BigInteger n = BigInteger.valueOf(_publicKey.get_n());

        // Set variable c to 1
        BigInteger c = BigInteger.valueOf(1);

        for(int i = 0; i <= binary.length() -1; i++)
        {
            // Step 1: C = C^2 (mod n)
            c = (c.multiply(c)).mod(n);

            // Step 2: If binary = 1, C = C x M (mod n)
            if(binary.charAt(i) == '1') {

                c = (c.multiply(m).mod(n));
            }
        }

        return String.valueOf(c);
    }

    /**
     * Method accepts blocks of cipherText and returns the decrypted text.
     *
     * @param cipherText - Block from the cipherText to be decrypted
     * @return decrypted block to be appended to plain text
     */
    /*public String decryptBlock(String cipherText) {

        // TODO Take d and n from the key, find a way to hold very large numbers for calc with these

        // To encrypt:
        // C = C^d(mod n)

        // Get value of D and convert to binary
        String binary = Integer.toBinaryString(3);
        System.out.println("Binary is: " + binary);
        System.out.println("C is: " + Integer.parseInt(cipherText));

        // Get value of N to be used in modulus expression
        int n = 33;
        System.out.println("N is: " + n);
        System.out.println("D is: " + 3);

        // Set variable c to 1
        long m = 1;

        for(int i = 0; i <= binary.length() -1; i++)
        {
            // Step 1: M = M^2 (mod n)
            m = m * m % n;

            // Step 2: If binary = 1, M = M x C (mod n)
            if(binary.charAt(i) == '1') {
                m = (m * Integer.parseInt(cipherText) % n);
            }
        }

        return Long.toString(m);
    }*/
}

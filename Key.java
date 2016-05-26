package com.iss5.encryption.rsa;

public class Key {

    private int _exponent = 0;
    private int _modulus = 0;
    private int _p, _q, _n, _w, _e, _d;

    public Key() {}

    public Key(int modulus, int exponent) {
        _modulus = modulus;
        _exponent = exponent;
    }

    /**
     * Method to generate keys, used by RSA.java
     *
     * @param keyType - Specify which key to generate
     * @return public or private key
     */
    public Key generateKey(String keyType) {

        generateValues();

        switch (keyType) {
            case "public":
                return new Key(_e, _n);
            case "private":
                return new Key(_d, _n);
            default:
                return null;
        }
    }

    /**
     * Method to calculate values for N, W and E.
     * For the purpose of this assignment D is given here.
     * P and Q are also specified in this method.
     */
    private void generateValues() {

        // Change P and Q here
        _p = 29;
        _q = 41;

        // Calculate n
        _n = ComputeN(_p, _q);

        // Calculate w
        _w = ComputeW(_p, _q);

        // Calculate d
        _d = 83;

        // Calculate e
        _e =  ComputeE(_d, _w);
    }

    private int ComputeN(int p, int q) {
        return p * q;
    }

    private int ComputeW(int p, int q) {
        return (p - 1) * (q - 1);
    }

    private int ComputeE(int d, int w) {

        return EE(w, d, 0, 1, 1, 0);
    }

    /**
     * Recursive call to compute E.
     *
     * @return value of e.
     */
    private int EE (int a, int b, int c, int d, int e, int f)
    {
        if (b == 0)
        {
            int [] ret = {0,0,0};
            ret [0] = a; // gcd(a,b)
            ret [1] = e; // coefficient of a
            ret [2] = f; // coefficient of b
            return ret[2];
        }
        else
        {
            return EE(b, a%b, e-(a/b)*c, f-(a/b)*d, c, d);
        }
    }

    /**
     * Method to return the value of a key for logging.
     *
     * @return String representation of the key
     */
    public String keyValue() {
        return "(" + _modulus + ", " + _exponent + ")";
    }

    /**
     * Method used to return the value of E for a public key,
     * this is used in encryption.
     *
     * @return integer value of E
     */
    public int get_e() { return _modulus; }

    /**
     * Method used to return the value of N from the key,
     * this is used in encryption.
     *
     * @return integer value of N
     */
    public int get_n() { return _exponent; }

    /**
     * Method used to return the value of D for a private key,
     * this is used in decryption.
     *
     * @return integer value of D
     */
    public int get_d() { return _modulus; }

}

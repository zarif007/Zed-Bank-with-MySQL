package Project;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class Hashing {

//-----------------------------Encryption happens Here with SHA-256 Algorithm-----------------------


//hashing Algorithm from - https://medium.com/@TechExpertise/java-cryptographic-hash-functions-a7ae28f3fa42

    public static String getCryptoHash(String input, String algorithm,int count) {
        try {
            MessageDigest msgDigest = MessageDigest.getInstance(algorithm);

            //digest() method is called to calculate message digest of the input
            //digest() return array of byte.
            byte[] inputDigest = msgDigest.digest(input.getBytes());

            // Convert byte array into signum representation
            // BigInteger class is used, to convert the resultant byte array into its signum representation
            BigInteger inputDigestBigInt = new BigInteger(1, inputDigest);

            // Convert the input digest into hex value
            String hashtext = inputDigestBigInt.toString(16);
            String s=String.valueOf(count%500);

            //Add preceding s to pad the hash text to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = s + hashtext;
            }
            return hashtext;
        }
        // Catch block to handle the scenarios when an unsupported message digest algorithm is provided.
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}



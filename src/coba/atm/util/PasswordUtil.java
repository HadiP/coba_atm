package coba.atm.util;

import coba.atm.service.PasswordEncoder;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.stream.IntStream;

/**
 * PasswordEncoder implementation
 */
public final class PasswordUtil implements PasswordEncoder {

    private static byte[] salt = null;

    public String encode(String original) {
        try {
            if(salt == null) {
                salt = new byte[16];
                new SecureRandom().nextBytes(salt);
            }
            KeySpec spec = new PBEKeySpec(original.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return DatatypeConverter.printHexBinary(factory.generateSecret(spec).getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean match(String hashed, String original) {
        return encode(original).equals(hashed);
    }

    /**
     * Generate random number
     * @return %d
     */
    public static String generateRandom(int digit){
        final SecureRandom secureRandom = new SecureRandom();
        return IntStream.iterate(0, i -> secureRandom.nextInt(10))
                .limit(digit)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
    }

}

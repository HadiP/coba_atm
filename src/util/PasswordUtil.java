package util;

import service.PasswordEncoder;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

/**
 * PasswordEncoder implementation
 */
public class PasswordUtil implements PasswordEncoder {

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

}

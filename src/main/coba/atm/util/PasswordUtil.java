package coba.atm.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * Password utility class.
 * Used to hash the password to prevent data tempering,
 * using PBKDF2 With HMAC SHA1 algorithm.
 */
public final class PasswordUtil {

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
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean match(String hashed, String original) {
        return Objects.requireNonNull(encode(original)).equals(hashed);
    }

}

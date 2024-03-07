package coba.atm.util;

import java.security.SecureRandom;
import java.util.stream.IntStream;

public class ReferenceNumberUtil {

    public static final int DEFAULT_DIGITS = 6;

    private ReferenceNumberUtil(){}

    /**
     * Generate random number based on digit length (default 6).
     *
     * @param digit (digit length)
     * @return d%
     */
    public static String generateRandom(int digit){
        final SecureRandom secureRandom = new SecureRandom();
        return IntStream.iterate(0, i -> secureRandom.nextInt(10))
                .limit(digit)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
    }

    /**
     * Generate random number based on digit length (default 6).
     *
     * @return d%
     */
    public static String generateRandom(){
        return generateRandom(DEFAULT_DIGITS);
    }

}

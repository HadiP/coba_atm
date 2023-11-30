package service;

public interface PasswordEncoder {
    String encode(String raw);

    boolean match(String hashed, String original);
}

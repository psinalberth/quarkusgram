package com.github.psinalberth.user.application.port.out;

public interface PasswordMatcherGateway {

    boolean passwordMatches(String rawPassword, String encryptedPassword);
}

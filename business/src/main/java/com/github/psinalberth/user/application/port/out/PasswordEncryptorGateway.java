package com.github.psinalberth.user.application.port.out;

public interface PasswordEncryptorGateway {

    String encrypt(String rawPassword);
}

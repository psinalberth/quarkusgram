package com.github.psinalberth.user.adapter.crypto;

import com.github.psinalberth.user.application.port.out.PasswordEncryptorGateway;
import com.github.psinalberth.user.application.port.out.PasswordMatcherGateway;
import lombok.RequiredArgsConstructor;
import org.jasypt.util.password.PasswordEncryptor;

@RequiredArgsConstructor
public class JasyptPasswordEncryptor implements PasswordEncryptorGateway, PasswordMatcherGateway {

    private final PasswordEncryptor delegate;

    @Override
    public String encrypt(String rawPassword) {
        return delegate.encryptPassword(rawPassword);
    }

    @Override
    public boolean passwordMatches(String rawPassword, String encryptedPassword) {
        return delegate.checkPassword(rawPassword, encryptedPassword);
    }
}

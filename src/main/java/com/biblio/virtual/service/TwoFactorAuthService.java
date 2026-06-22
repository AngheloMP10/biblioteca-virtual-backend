package com.biblio.virtual.service;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import org.springframework.stereotype.Service;

@Service
public class TwoFactorAuthService {

    private static final String ISSUER = "BibliotecaVirtual";
    private final GoogleAuthenticator googleAuthenticator;

    public TwoFactorAuthService() {
        this.googleAuthenticator = new GoogleAuthenticator();
    }

    public String generateSecret() {
        GoogleAuthenticatorKey key = googleAuthenticator.createCredentials();
        return key.getKey();
    }

    public boolean validateCode(String secret, int code) {
        return googleAuthenticator.authorize(secret, code);
    }

    public String generateQrUrl(String username, String secret) {
        return String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s",
                ISSUER, username, secret, ISSUER);
    }
}

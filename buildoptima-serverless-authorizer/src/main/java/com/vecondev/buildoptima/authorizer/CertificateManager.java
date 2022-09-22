package com.vecondev.buildoptima.authorizer;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.lang.Assert;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class CertificateManager {

  public static final String PUBLIC_KEY_PATH = "key/public.der";

  private CertificateManager() {}

  public static PublicKey publicKey() {
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.forName(
        JwtConfigProperties.getSignatureAlgorithm());
    Assert.notNull(PUBLIC_KEY_PATH, "Public key path is required");
    try {
      KeyFactory keyFactory = KeyFactory.getInstance(signatureAlgorithm.getFamilyName());
      return readKey(keyFactory);
    } catch (Exception e) {
      throw new PublicKeyException();
    }
  }

  private static PublicKey readKey(KeyFactory keyFactory) throws IOException {
    try {
      return keyFactory.generatePublic(
          new X509EncodedKeySpec(Files.readAllBytes(Paths.get(PUBLIC_KEY_PATH))));
    } catch (InvalidKeySpecException e) {
      throw new PublicKeyException();
    }
  }
}
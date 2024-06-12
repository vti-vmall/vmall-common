package vn.edu.vti.vmall.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import vn.edu.vti.vmall.security.config.JWTTokenProperties;

@Configuration
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtil {
  private final JWTTokenProperties tokenProperties;
  private byte[] generateKey(String key) {
    try {
      MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
      byte[] hash = sha256.digest(key.getBytes(StandardCharsets.UTF_8));
      return Arrays.copyOf(hash, 32); // Ensure the key is exactly 32 bytes long
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("SHA-256 algorithm not found", e);
    }
  }

  private SecretKey getKey(){
    byte[] keyBytes = generateKey(tokenProperties.getKey());
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public String genToken(String username, String issuer){
    return Jwts.builder()
        .issuer(issuer)
        .issuedAt(new Date()) // Set the issue time
        .expiration(new Date(System.currentTimeMillis() + 3600000))
        .claim("username", username) // Set custom claims
        .signWith(getKey()) // Sign the JWT with the key
        .compact();
  }

  public Claims validate(String token){
    try {
      return Jwts.parser().
          verifyWith(getKey())
          .build()
          .parseSignedClaims(token)
          .getPayload();
    }catch (Exception e){
      log.error("(validate)Exception with message: [{}]",
          e.getMessage());
    }
    return null;
  }
}

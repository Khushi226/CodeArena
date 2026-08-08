
// package com.codearena.backend.security;

// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.security.Keys;
// import org.springframework.stereotype.Component;

// import java.nio.charset.StandardCharsets;
// import java.security.Key;
// import java.util.Date;

// @Component
// public class JwtUtil {

//     // ✅ MUST be at least 32 characters for HS256
//     private static final String SECRET =
//             "codearena_super_secret_key_please_change_123456";

//     private static final long EXPIRATION_MS = 86400; // 1 day

//     private final Key key;

//     public JwtUtil() {
//         this.key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
//     }

//     // ✅ Generate token with username + userId
//     public String generateToken(String username, Long userId) {

//         return Jwts.builder()
//                 .setSubject(username)
//                 .claim("userId", userId)
//                 .setIssuedAt(new Date())
//                 .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
//                 .signWith(key)
//                 .compact();
//     }

//     // ✅ Extract username
//     public String extractUsername(String token) {
//         return extractAllClaims(token).getSubject();
//     }

//     // ✅ Extract userId
//     public Long extractUserId(String token) {
//         Object val = extractAllClaims(token).get("userId");

//         if (val == null) return null;

//         if (val instanceof Integer) return ((Integer) val).longValue();
//         if (val instanceof Long) return (Long) val;

//         return Long.parseLong(val.toString());
//     }

//     // ✅ Validate token
//     public boolean validateToken(String token, String username) {
//         String extractedUsername = extractUsername(token);
//         return extractedUsername.equals(username) && !isTokenExpired(token);
//     }

//     private boolean isTokenExpired(String token) {
//         return extractAllClaims(token).getExpiration().before(new Date());
//     }

//     private Claims extractAllClaims(String token) {

//         return Jwts.parserBuilder()
//                 .setSigningKey(key)
//                 .build()
//                 .parseClaimsJws(token)
//                 .getBody();
//     }
// }






package com.codearena.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey key;
    private final JwtParser jwtParser;
    private final long expirationMs;

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-ms:86400000}") long expirationMs) {

        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMs = expirationMs;
        
        // Build and reuse a single parser instance for better performance
        this.jwtParser = Jwts.parserBuilder()
                .setSigningKey(this.key)
                .build();
    }

    // ✅ Generate token with username + userId
    public String generateToken(String username, Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    // ✅ Extract username (returns null if token is expired or invalid)
    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims != null ? claims.getSubject() : null;
    }

    // ✅ Extract userId safely (returns null if token is expired or invalid)
    public Long extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        if (claims == null) return null;

        Object val = claims.get("userId");
        if (val == null) return null;

        if (val instanceof Number) {
            return ((Number) val).longValue();
        }
        return Long.parseLong(val.toString());
    }

    // ✅ Validate token (JJWT handles expiration automatically during parsing)
    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return extractedUsername != null && extractedUsername.equals(username);
    }

    // Helper method to extract claims safely without throwing unhandled exceptions
    private Claims extractAllClaims(String token) {
        try {
            return jwtParser.parseClaimsJws(token).getBody();
        } catch (JwtException | IllegalArgumentException e) {
            // Token is expired, tampered, or malformed
            return null;
        }
    }
}
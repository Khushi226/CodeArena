// package com.codearena.backend.security;

// import java.security.Key;
// import java.util.Date;

// import org.springframework.stereotype.Component;
// import com.codearena.backend.entity.*;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.security.Keys;

// @Component
// public class JwtUtil {

//     private static final String SECRET = "codearena_secret_key_codearena_secret_key";

//     private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

//     public String generateToken(String username,Long userId) {
//         return Jwts.builder()
//                 .setSubject(username)
//                 .claim("userId", userId)
//                 .setIssuedAt(new Date())
//                 .setExpiration(new Date(System.currentTimeMillis() + 86400000))
//                 .signWith(key, SignatureAlgorithm.HS256)
//                 .compact();
//     }
//     public String extractUsername(String token) {
//     return Jwts.parserBuilder()
//             .setSigningKey(key)
//             .build()
//             .parseClaimsJws(token)
//             .getBody()
//             .getSubject();
//     }

//     public boolean validateToken(String token) {
//         try {
//             Jwts.parserBuilder()
//                 .setSigningKey(key)
//                 .build()
//                 .parseClaimsJws(token);
//             return true;
//         } catch (Exception e) {
//             return false;
//         }
//     }

// }


package com.codearena.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // ✅ MUST be at least 32 characters for HS256
    private static final String SECRET =
            "codearena_super_secret_key_please_change_123456";

    private static final long EXPIRATION_MS = 86400000; // 1 day

    private final Key key;

    public JwtUtil() {
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    // ✅ Generate token with username + userId
    public String generateToken(String username, Long userId) {

        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(key)
                .compact();
    }

    // ✅ Extract username
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // ✅ Extract userId
    public Long extractUserId(String token) {
        Object val = extractAllClaims(token).get("userId");

        if (val == null) return null;

        if (val instanceof Integer) return ((Integer) val).longValue();
        if (val instanceof Long) return (Long) val;

        return Long.parseLong(val.toString());
    }

    // ✅ Validate token
    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return extractedUsername.equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}


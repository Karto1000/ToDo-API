package ch.bbzsogr.todo.service;

import ch.bbzsogr.todo.model.User;
import ch.bbzsogr.todo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

@Service
public class JWTService {
    @Value("${token.validation.time}")
    private int tokenValidityMilliseconds;
    @Value("${token.validation.key}")
    private String key;
    @Autowired
    private UserRepository userRepository;

    public String generateToken(UserDetails userDetails) throws Exception {
        // Username is Email lol
        Optional<User> optionalUser = userRepository.getUserByEmail(userDetails.getUsername());
        if (optionalUser.isEmpty()) throw new Exception("User does not exist");

        User user = optionalUser.get();

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenValidityMilliseconds))
                .claim("id", user.getUserId())
                .signWith(getShaKey())
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getShaKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public <T> T extractClaims(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts.parser()
                .verifyWith(getShaKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return resolver.apply(claims);
    }

    public SecretKey getShaKey() {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

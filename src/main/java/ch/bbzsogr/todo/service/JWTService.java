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

    /**
     * Generate a new token for the specified user
     *
     * @param userDetails The user details to generate a token for
     * @return The generated JWT
     * @throws Exception If the user could not be found
     */
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

    /**
     * Check if a specified JWT is valid
     *
     * @param token The token to check
     * @return If the token is valid
     */
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

    /**
     * Extract a variable from the JWT
     *
     * @param token    The token to extract the claims from
     * @param resolver A Function converts the claim to the expected value
     * @param <T>      The type of the value that is returned from the resolver function
     * @return The extracted claim
     */
    public <T> T extractClaims(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts.parser()
                .verifyWith(getShaKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return resolver.apply(claims);
    }

    /**
     * Function to generate a hmacShaKey from the key application property
     *
     * @return The created hmacSha key
     */
    public SecretKey getShaKey() {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

package com.luizfoli.literarbff.config.jwt;

import com.luizfoli.literarbff.model.User;
import com.luizfoli.literarbff.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class JwtUtil {

    @Value("{jwt.secret}")
    private String secretKey;
    private int lifecyle = 1000 * 60 * 60 * 10;

    private UserRepository repository;

    public JwtUtil(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * @param userId
     * @return JWT Token
     */

    public String generateToken(long userId) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userId);
    }

    /**
     * @param token
     * @return
     */

    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * @param token
     * @return
     */

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * @param token
     * @param claimsResolver
     * @param <T>
     * @return
     */

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * @param claims
     * @param userId
     * @return
     */

    private String createToken(Map<String, Object> claims, Long userId) {
        return Jwts.builder().setClaims(claims)
                .setSubject(userId.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + lifecyle))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    /**
     * @param token
     * @return
     */

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    /**
     * @param token
     * @return
     */

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     *
     * @param token
     * @return
     */

    public Boolean validateToken(String token) {
        String id = extractUserId(token);
        if(id == null) {
            return false;
        }
        final Optional<User> user = this.repository.findById(Long.parseLong(id));
        return ( user.isPresent() && !isTokenExpired(token));
    }

}

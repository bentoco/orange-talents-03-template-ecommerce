package br.com.zupacademy.ecommerce.config.security;

import br.com.zupacademy.ecommerce.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenManager {

    @Value ( "${jwt.secret}" )
    private String secret;

    @Value ( "${jwt.expiration}" )
    private long expirationInMillis;

    public String generateToken ( Authentication authentication ) {

        User logged = (User) authentication.getPrincipal();

        final Date now = new Date();
        final Date expiration = new Date(now.getTime() + this.expirationInMillis);

        return Jwts.builder()
                .setIssuer("Challenger Orange Talents - Mercado Livre")
                .setSubject(logged.getId().toString())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256 , this.secret)
                .compact();
    }

    public boolean isValid ( String jwt ) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(jwt);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Long getIdUsuario ( String jwt ) {
        Claims claims = Jwts.parser().setSigningKey(this.secret)
                .parseClaimsJws(jwt).getBody();
        return Long.parseLong(claims.getSubject());
    }
}

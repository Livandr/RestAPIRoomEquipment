package com.technobel.restapiroomequipment.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.technobel.restapiroomequipment.models.entities.users.Person;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class JwtProvider {

    private static final String JWT_SECRET = "UTC.ZO\"7%0u7.ieT_f`nsQd)8Z',yp/7k[N;#D%zgrY\"z{Bheg04(O)\"H&~W\"Jv";
    private static final long EXPIRES_AT = 86_400_000;
    private static final String AUTH_HEADER= "Authorization";
    private static final  String TOKEN_PREFIX = "Bearer ";
    private final UserDetailsService userDetailsService;

    private JwtProvider(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    @SneakyThrows
    public String generateToken(String username, String role){

        return TOKEN_PREFIX + JWT.create()
                .withExpiresAt(Date.from(Instant.now().minusMillis(EXPIRES_AT)))
                .withSubject(username)
                .sign(Algorithm.HMAC512(JWT_SECRET));
    }


    public String extractToken(HttpServletRequest req) {

        String authReader = req.getHeader(AUTH_HEADER);

        if(authReader == null || !authReader.startsWith(TOKEN_PREFIX))
            return null;

        return authReader.replaceFirst(TOKEN_PREFIX, "");
    }

    public boolean validateToken(String token){

        try {
            //1. le bon code secret est utilisé avec le bon algo
            //2. pas expiré

            DecodedJWT jwt = JWT.require(Algorithm.HMAC512(JWT_SECRET))
                    .acceptExpiresAt(EXPIRES_AT)
                    .build().verify(token);

            //3. généré à partir d'un user existant
            String username = jwt.getSubject();
            Person person = (Person) userDetailsService.loadUserByUsername(username);

            return true;
            }
        catch (JWTVerificationException | UsernameNotFoundException exception){
            return false;
        }
    }

    public Authentication createAuthentication(String token){

        DecodedJWT jwt = JWT.decode(token);
        String username = jwt.getSubject();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                null,
                userDetails.getAuthorities()
        );
    }

}

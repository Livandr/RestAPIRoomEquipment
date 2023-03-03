package com.technobel.restapiroomequipment.config;

import com.technobel.restapiroomequipment.utils.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    public JwtAuthenticationFilter(JwtProvider jwtProvider){
        this.jwtProvider = jwtProvider;
    }

    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            //1. extraire le token
            String token = jwtProvider.extractToken(request);

            //2. valider le token
            if (token != null && jwtProvider.validateToken(token)) {

            //3. récupérer la pers liée au token
            //4. créer une authentification
            Authentication auth = jwtProvider.createAuthentication(token);

            //5. placer l'authentification dans le SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
            filterChain.doFilter(request, response);
    }
}

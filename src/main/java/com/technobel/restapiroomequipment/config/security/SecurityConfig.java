package com.technobel.restapiroomequipment.config;

import com.technobel.restapiroomequipment.models.entities.users.Teacher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthFilter) throws Exception{

        http.csrf().disable();

        http.httpBasic().disable();

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests(
                registry -> registry
//                        .requestMatchers("/security/test/permit-all").permitAll()
                        .requestMatchers("/security/test/non-existent").denyAll()

                        .requestMatchers("/security/test/connected").authenticated()
                        .requestMatchers("/security/test/not-connected").anonymous()

                        .requestMatchers("/security/test/role_user").hasRole("ADMIN")
                        .requestMatchers("/security/test/role_user").hasAuthority("ADMIN")
                        .requestMatchers("/security/test/any_role").hasAnyRole("ADMIN","TEACHER","STUDENT")
                        .requestMatchers("/security/test/has_authority_role_user").hasAuthority("ROLE_USER")
                        .requestMatchers("/security/test/has_any_authority").hasAnyAuthority("ROLE_ADMIN","TEST","READ")

                        .requestMatchers("/security/test/**").denyAll()
                        .requestMatchers("/security/test/*", "/security/t?st").denyAll()

                        .requestMatchers(HttpMethod.POST).hasRole("ADMIN")

                        .requestMatchers( request -> request.getRequestURI().length() > 50 ).denyAll()

                        .requestMatchers(HttpMethod.POST, "/room/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/room/**").authenticated()
                        .requestMatchers(HttpMethod.PUT).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/room/**").hasAnyRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/auth/register").hasRole("STUDENT")
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()


                        .requestMatchers(HttpMethod.POST,"/request/new").hasAnyRole("STUDENT", "TEACHER")
                        .requestMatchers(HttpMethod.DELETE,"/request/cancel").hasAnyRole("STUDENT", "TEACHER")

                        .anyRequest().permitAll()
        );

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }
}

package com.technobel.restapiroomequipment.config.security;

import com.technobel.restapiroomequipment.config.security.JwtAuthenticationFilter;
import com.technobel.restapiroomequipment.models.forms.RegisterUserForm;
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

//                        .requestMatchers(HttpMethod.POST).hasRole("ADMIN")

//                        .requestMatchers( request -> request.getRequestURI().length() > 50 ).denyAll()

                        //Room
//                        .requestMatchers(HttpMethod.POST, "/room/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/room/all").authenticated()

                        .requestMatchers(HttpMethod.PUT, "/room/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/room/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/room/**").hasRole("ADMIN")


                        //Auth
                        .requestMatchers(HttpMethod.POST, "/auth/register").anonymous()

                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()



                        //Request
                        .requestMatchers(HttpMethod.GET,"/request/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/request/all").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET,"/request/{id:[0-9]+}/**").hasAnyRole("ADMIN","TEACHER","STUDENT" )

                        .requestMatchers(HttpMethod.PATCH, "/request/{id:[0-9]+}").hasAnyRole("ADMIN","TEACHER","STUDENT" )
                        .requestMatchers(HttpMethod.POST,"/request/new").hasAnyRole("STUDENT", "TEACHER")
                        .requestMatchers(HttpMethod.DELETE,"/request/cancel").hasAnyRole("STUDENT", "TEACHER")

                        .anyRequest().authenticated()
        );

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }
}

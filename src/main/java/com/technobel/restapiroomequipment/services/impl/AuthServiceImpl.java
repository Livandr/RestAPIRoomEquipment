package com.technobel.restapiroomequipment.services.impl;

import com.technobel.restapiroomequipment.models.dto.PersonDTO;
import com.technobel.restapiroomequipment.models.entities.users.Person;
import com.technobel.restapiroomequipment.models.forms.LoginForm;
import com.technobel.restapiroomequipment.models.forms.RegisterUserForm;
import com.technobel.restapiroomequipment.repositories.PersonRepository;
import com.technobel.restapiroomequipment.services.AuthService;
import com.technobel.restapiroomequipment.utils.JwtProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authManager;
    private final PersonRepository personRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(
            AuthenticationManager authManager,
            PersonRepository personRepository,
            JwtProvider jwtProvider,
            PasswordEncoder passwordEncoder
            )
    {
        this.authManager = authManager;
        this.personRepository = personRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public PersonDTO register(RegisterUserForm form) {

        Person person = form.toEntity();
        person.setPassword(passwordEncoder.encode(form.getPassword()));
        person = personRepository.save(person);

        String token = jwtProvider.generateToken(person.getUsername(), person.getRole() );

        return PersonDTO.builder()
                .token(token)
                .username(person.getUsername())
                .build();
    }

    @Override
    public PersonDTO login(LoginForm form) {

        authManager.authenticate(new UsernamePasswordAuthenticationToken(form.getUsername(),form.getPassword()));

        Person person = personRepository.findByUsername(form.getUsername())
                .orElseThrow();

        String token = jwtProvider.generateToken(person.getUsername(), person.getRole());

        return PersonDTO.builder()
                .token(token)
                .username(person.getUsername())
                .build();

    }
}

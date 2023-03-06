package com.technobel.restapiroomequipment.controller;

import com.technobel.restapiroomequipment.models.dto.PersonDTO;
import com.technobel.restapiroomequipment.models.forms.LoginForm;
import com.technobel.restapiroomequipment.models.forms.RegisterUserForm;
import com.technobel.restapiroomequipment.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    public AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }


    //POST - http://localhost:8080/auth/register
    @PostMapping("/register")
    public PersonDTO register(@RequestBody @Valid RegisterUserForm form){
        return authService.register(form);
    }

    //POST - http://localhost:8080/auth/login
    @PostMapping("/login")
    public PersonDTO login(@RequestBody @Valid LoginForm form){
        return authService.login(form);
    }
}

package com.technobel.restapiroomequipment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security/test")
public class SecurityTestController {

    @GetMapping("/permit-all")
    public String permitAll(){
        return "ok";
    }

    @GetMapping("/any-role")
    public String hasAnyRole(){
        return "ok";
    }

    @GetMapping("/role-user")
    public String hasRoleUser(){
        return "ok";
    }

    @GetMapping("/role-admin")
    public String hasRoleAdmin(){
        return "ok";
    }

    @GetMapping("/connected")
    public String connected(){
        return "ok";
    }

    @GetMapping("/not-connected")
    public String notConnected(){
        return "ok";
    }

    @GetMapping("/has_authority_role_user")
    public String hasAutority(){
        return "ok";
    }

    @GetMapping("/has_any_authority")
    public String hasAnyAuthority(){
        return "ok";
    }
}

package com.technobel.restapiroomequipment.services.impl;

import com.technobel.restapiroomequipment.repositories.PersonRepository;
import com.technobel.restapiroomequipment.services.PersonService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    private final AuthenticationManager authManager;

    private final PersonRepository personRepository;
    private final
}

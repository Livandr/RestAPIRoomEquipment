package com.technobel.restapiroomequipment.services.impl;

import com.technobel.restapiroomequipment.models.forms.LoginForm;
import com.technobel.restapiroomequipment.repositories.PersonRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PersonRepository personRepository;

    public UserDetailsServiceImpl(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return personRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("couldn't find user with " + username));
    }

    public void login(LoginForm form) {
        personRepository.existsByUsernameAndPassword(form.getUsername(), form.getPassword());
    }
}

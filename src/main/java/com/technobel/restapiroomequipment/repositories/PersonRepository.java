package com.technobel.restapiroomequipment.repositories;

import com.technobel.restapiroomequipment.models.entities.users.Person;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {


    //Query ("select p from Person p where p.login = ?")
    Optional<Person> findByUsername(String username);

    void existsByUsernameAndPassword(String username, String password);
}

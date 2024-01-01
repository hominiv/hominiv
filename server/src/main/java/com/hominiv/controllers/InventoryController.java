package com.hominiv.controllers;

import com.hominiv.records.Person;
import com.hominiv.services.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLClientInfoException;
import java.util.List;

@RestController
public class InventoryController {

    private final PersonService personService;

    public InventoryController(final PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/him")
    public List<Person> getPersons() {
        return personService.getPersons();
    }

    @PutMapping("/him")
    public Person createPerson(
            @RequestBody() Person person) throws SQLClientInfoException {
        return personService.createPerson(person);
    }
}

package com.hominiv.controllers;

import com.hominiv.records.Person;
import com.hominiv.services.PersonService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLClientInfoException;
import java.util.List;

@RestController
@CrossOrigin
public class InventoryController {

    private final PersonService personService;

    public InventoryController(final PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/him")
    public List<Person> getPersons(
        @RequestParam(name = "isHim", required = false) final Boolean isHim) {
        return personService.getPersons(isHim);
    }

    @GetMapping("/him/{userId}")
    public ResponseEntity<Person> getPersonByUserId(
        @PathVariable(name = "userId") final Long userId) {
        try {
            return ResponseEntity.ok(personService.getPersonByUserId(userId));
        } catch (BadRequestException bre) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/him")
    public ResponseEntity<Person> createPerson(
            @RequestBody() final Person person) {
        try {
            return ResponseEntity.ok(personService.createPerson(person));
        } catch (SQLClientInfoException sqlie) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping("/him/{userId}")
    public ResponseEntity<Person> updatePerson(
            @PathVariable(name = "userId") final Long userId,
            @RequestParam(name = "isHim") final Boolean isHim) {
        try {
            return ResponseEntity.ok(personService.updatePerson(userId, isHim));
        } catch (BadRequestException bre) {
            return ResponseEntity.badRequest().build();
        } catch (SQLClientInfoException sqlcie) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/him/{userId}")
    public ResponseEntity<Person> deletePerson(
            @PathVariable(name = "userId") final Long userId) {
        try {
            return ResponseEntity.ok(personService.deletePerson(userId));
        } catch (BadRequestException bre) {
            return ResponseEntity.badRequest().build();
        }
    }
}

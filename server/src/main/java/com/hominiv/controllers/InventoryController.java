package com.hominiv.controllers;

import com.hominiv.records.Person;
import com.hominiv.services.PersonService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLClientInfoException;
import java.util.List;

/**
 * This is a rest controller, aka the home of our HTTP endpoints.
 * These endpoints serve as entryways into our service, allow us to
 * take predetermined paths to-and-from our data.
 * <p>
 * Springboot gives us a host of annotations to use that allows it to
 * generate endless amounts of code at compile time. I'll try to define
 * as many of them as I can here.
 *
 * @RestController - allows Spring to bind sockets and set up URL paths
 *                   for us to access externally
 *
 * @CrossOrigin - solves some CORs related errors I was getting when making
 *                this demo. Basically prevents some security restrictions
 *                that otherwise cause the server to shut down requests from
 *                sources it isn't expecting.
 */
@RestController
@CrossOrigin
public class InventoryController {

    private final PersonService personService;

    /**
     * Spring automatically finds suitable java beans to inject into our constructors
     * for us. This saves us endless trouble with large applications maintaining a lot
     * of variable dependencies.
     * <p>
     * <p>
     * It's implicitly added in new Spring versions, but:
     * @Autowired - defines that all class dependencies should be automatically wired
     *              into the annotated constructor.
     */
    InventoryController(final PersonService personService) {
        this.personService = personService;
    }

    /**
     * @RequestMapping - this takes many forms, @GetMapping, @PutMapping, @PostMapping,
     *                   and @DeleteMapping. These annotations define an HTTP method and
     *                   URL path as an entry point into the service.
     */
    @GetMapping("/him")
    public List<Person> getPersons(
        /**
         * @RequestParam - allows Spring to automatically deserialize request params from
         *                 the request (remember - "?paramName=paramValue&...")
         */
        @RequestParam(name = "isHim", required = false) final Boolean isHim) {
        return personService.getPersons(isHim);
    }

    @GetMapping("/him/{userId}")
    public ResponseEntity<Person> getPersonByUserId(
        /**
         * @PathVariable - here, defined in the {userId} part of the @GetMapping value.
         *                 This is just a unique value that we pass into our request
         *                 without any other parameter context. We assume the client
         *                 knows what this should be, and it's always required
         * <p>
         * Note: path variables are good to use with hidden values, like "userId" on our
         *       person object. This obfuscates some of the details about the request from
         *       would be "attackers"
         */
        @PathVariable(name = "userId") final Long userId) {
        try {
            return ResponseEntity.ok(personService.getPersonByUserId(userId));
        } catch (BadRequestException bre) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/him")
    public ResponseEntity<Person> createPerson(
        /**
         * @RequestBody - indicates a type used in the body of our HTTP request. Spring
         *                handles the JSON serialization/deserialization for us nowadays.
         */
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

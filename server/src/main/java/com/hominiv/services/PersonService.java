package com.hominiv.services;

import com.hominiv.records.Person;
import org.apache.coyote.BadRequestException;

import java.sql.SQLClientInfoException;
import java.util.List;

public interface PersonService {
    List<Person> getPersons();

    Person createPerson(final Person person) throws SQLClientInfoException;

    Person updatePerson(final Long userId, final Boolean isHim) throws SQLClientInfoException, BadRequestException;

    Person deletePerson(final Long userId) throws BadRequestException;
}

package com.hominiv.services;

import com.hominiv.records.Person;

import java.sql.SQLClientInfoException;
import java.util.List;

public interface PersonService {
    List<Person> getPersons();

    Person createPerson(final Person person) throws SQLClientInfoException;
}

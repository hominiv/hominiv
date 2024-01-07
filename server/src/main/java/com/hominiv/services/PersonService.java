/* (C)2024 */
package com.hominiv.services;

import com.hominiv.records.Person;
import java.sql.SQLClientInfoException;
import java.util.List;
import org.apache.coyote.BadRequestException;

public interface PersonService {
    List<Person> getPersons(final Boolean isHim);

    Person getPersonByUserId(final Long userId) throws BadRequestException;

    Person createPerson(final Person person) throws SQLClientInfoException;

    Person updatePerson(final Long userId, final Boolean isHim)
            throws SQLClientInfoException, BadRequestException;

    Person deletePerson(final Long userId) throws BadRequestException;
}

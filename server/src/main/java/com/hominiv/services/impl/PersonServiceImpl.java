package com.hominiv.services.impl;

import com.hominiv.mappers.PersonMapper;
import com.hominiv.records.Person;
import com.hominiv.resources.repositories.PersonRepository;
import com.hominiv.services.PersonService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLClientInfoException;
import java.util.List;
import java.util.Optional;

@Component("personService")
@Transactional
class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    PersonServiceImpl(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> getPersons() {
        return personRepository.findAll().stream()
                .map(PersonMapper::mapPersonDataObjectToPersonRecord)
                .toList();
    }

    @Override
    public Person createPerson(final Person person) throws SQLClientInfoException {
        return Optional.of(personRepository.saveAndFlush(PersonMapper.mapPersonRecordToPersonDataObject(person)))
                .map(PersonMapper::mapPersonDataObjectToPersonRecord)
                .orElseThrow(SQLClientInfoException::new);
    }
}

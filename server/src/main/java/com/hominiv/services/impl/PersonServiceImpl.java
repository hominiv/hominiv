package com.hominiv.services.impl;

import com.hominiv.mappers.PersonMapper;
import com.hominiv.records.Person;
import com.hominiv.resources.dataobjects.PersonDO;
import com.hominiv.resources.repositories.PersonRepository;
import com.hominiv.services.PersonService;
import org.apache.coyote.BadRequestException;
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

    @Override
    public Person updatePerson(final Long userId, final Boolean isHim) throws SQLClientInfoException, BadRequestException {
        return switch (personRepository.findById(userId).orElse(null)) {
            case PersonDO pDO -> {
                pDO.setIsHim(isHim);
                yield Optional.of(personRepository.saveAndFlush(pDO))
                    .map(PersonMapper::mapPersonDataObjectToPersonRecord)
                    .orElseThrow(SQLClientInfoException::new);
            }
            case null ->
                    throw new BadRequestException();
        };
    }

    @Override
    public Person deletePerson(final Long userId) throws BadRequestException {
        return switch (personRepository.findById(userId).orElse(null)) {
            case PersonDO p -> {
                personRepository.delete(p);
                yield PersonMapper.mapPersonDataObjectToPersonRecord(p);
            }
            case null ->
                throw new BadRequestException();
        };
    }
}

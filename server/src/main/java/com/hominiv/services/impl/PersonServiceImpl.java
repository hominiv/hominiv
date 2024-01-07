package com.hominiv.services.impl;

import com.hominiv.mappers.PersonMapper;
import com.hominiv.records.Person;
import com.hominiv.resources.dataobjects.PersonDO;
import com.hominiv.resources.repositories.PersonRepository;
import com.hominiv.services.PersonService;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLClientInfoException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @Service - generates a Spring bean of the annotated class, thus allowing it to be
 *            autowired into other components of our service
 *
 * @Transactional - indicates the annotated component as transaction based, meaning
 *                  data changes won't be committed in the event of any failures. This
 *                  is particularly useful in long streams of data fetching / mutating / storing
 */
@Service
@Transactional
class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    PersonServiceImpl(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> getPersons(final Boolean isHim) {
        if (Objects.nonNull(isHim)) {
            return personRepository.getPersonsByIsHimStatus(isHim).stream()
                .map(PersonMapper::mapPersonDataObjectToPersonRecord)
                .toList();
        }
        return personRepository.findAll().stream()
                .map(PersonMapper::mapPersonDataObjectToPersonRecord)
                .toList();
    }

    @Override
    public Person getPersonByUserId(final Long userId) throws BadRequestException {
        return personRepository.findById(userId)
            .map(PersonMapper::mapPersonDataObjectToPersonRecord)
            .orElseThrow(BadRequestException::new);
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

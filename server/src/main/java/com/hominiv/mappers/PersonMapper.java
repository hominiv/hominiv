package com.hominiv.mappers;

import com.hominiv.records.Person;
import com.hominiv.resources.dataobjects.PersonDO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PersonMapper {

    public static Person mapPersonDataObjectToPersonRecord(final PersonDO personDO) {
        return new Person(
                personDO.getIsHim(),
                personDO.getFirstName(),
                personDO.getLastName());
    }

    public static PersonDO mapPersonRecordToPersonDataObject(final Person person) {
        PersonDO personDO = new PersonDO();
        personDO.setFirstName(person.firstName());
        personDO.setLastName(person.lastName());
        personDO.setIsHim(person.isHim());
        return personDO;
    }
}

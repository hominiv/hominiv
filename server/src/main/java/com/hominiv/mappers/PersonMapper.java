package com.hominiv.mappers;

import com.hominiv.records.Person;
import com.hominiv.resources.dataobjects.PersonDO;
import lombok.experimental.UtilityClass;

/**
 * This isn't a Spring annotation. @UtilityClass is provided by project lombok.
 * Essentially it's just clearing some errors and preventing this class from ever
 * being instantiated. All static methods are expected with this.
 */
@UtilityClass
public class PersonMapper {

    public static Person mapPersonDataObjectToPersonRecord(final PersonDO personDO) {
        return new Person(
                personDO.getUserId(),
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

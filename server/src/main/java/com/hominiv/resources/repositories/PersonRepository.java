package com.hominiv.resources.repositories;

import com.hominiv.resources.dataobjects.PersonDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<PersonDO, Long> {
}

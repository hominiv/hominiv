package com.hominiv.resources.repositories;

import com.hominiv.resources.dataobjects.PersonDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<PersonDO, Long> {

    @Query("SELECT pdo FROM PersonDO pdo WHERE pdo.isHim = :isHim")
    List<PersonDO> getPersonsByIsHimStatus(final Boolean isHim);
}

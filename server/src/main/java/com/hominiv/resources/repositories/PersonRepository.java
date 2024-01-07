/* (C)2024 */
package com.hominiv.resources.repositories;

import com.hominiv.resources.dataobjects.PersonDO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @Repository - declares the interface a host for different queries and database commands
 */
@Repository
public interface PersonRepository extends JpaRepository<PersonDO, Long> {

    /**
     * @Query - a DBMS query in your specified query language to be run on
     *          the indicated method's execution.
     */
    @Query("SELECT pdo FROM PersonDO pdo WHERE pdo.isHim = :isHim")
    List<PersonDO> getPersonsByIsHimStatus(final Boolean isHim);
}

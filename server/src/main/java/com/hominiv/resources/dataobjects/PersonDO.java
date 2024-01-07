/* (C)2024 */
package com.hominiv.resources.dataobjects;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @Entity - Hibernate annotation declaring this class a database entity.
 *
 * @Getter,
 * @Setter - Getter and setter are from project lombok. They just generate some getter and setter methods for us
 *
 * @Table - indicates the name of the table in our database
 */
@Entity
@Getter
@Setter
@Table(name = "persons")
public class PersonDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", insertable = false, updatable = false)
    Long userId;

    @Column(name = "is_him")
    Boolean isHim;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;
}

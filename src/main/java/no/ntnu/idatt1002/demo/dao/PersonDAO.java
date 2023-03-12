package no.ntnu.idatt1002.demo.dao;

import no.ntnu.idatt1002.demo.data.person.Person;

import java.util.Collection;
import java.util.List;

public interface PersonDAO {

    /**
     * Adds a person.
     * @param   person the person
     * @return  true if the person was added
     */
    boolean add(Person person);

    /**
     * Finds a person with the given ID.
     * @param   id the id
     * @return  a person with the given ID or null if no one has the ID
     */
    Person find(int id);

    List<Person> find(Collection<Integer> ids);
}
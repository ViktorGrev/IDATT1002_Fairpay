package no.ntnu.idatt1002.demo.dao;

import no.ntnu.idatt1002.demo.data.user.User;

import java.util.Collection;
import java.util.List;

public interface UserDAO {

    /**
     * Adds a person.
     * @param   person the person
     * @return  true if the person was added
     */
    boolean insert(User user);

    /**
     * Finds a person with the given ID.
     * @param   id the id
     * @return  a person with the given ID or null if no one has the ID
     */
    User find(int id);

    /**
     * Finds a list of persons with the given IDs.
     * @param   ids the ids
     * @return  a list of persons with the given IDs.
     */
    List<User> find(Collection<Integer> ids);
}
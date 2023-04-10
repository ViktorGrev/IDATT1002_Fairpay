package no.ntnu.idatt1002.dao;

import no.ntnu.idatt1002.dao.exception.AuthException;
import no.ntnu.idatt1002.data.User;

/**
 * This interface defines methods to interact with users and user related tasks.
 */
public interface UserDAO extends DAO<User, Long> {

    User find(String username);

    /**
     * Creates a new user with the specified username, password
     * and phone number.
     * @param   username the username
     * @param   password the password
     * @param   phoneNumber the phone number
     * @return  a new user
     */
    User create(String username, String password, long phoneNumber);

    /**
     * Authenticates a user with a given username and password.
     * @param   username the username
     * @param   password the password
     * @return  the user with the specified username and password
     * @throws  AuthException if the password is invalid or the user is not found
     */
    User authenticate(String username, String password) throws AuthException;
}

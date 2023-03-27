package no.ntnu.idatt1002.controller;

import no.ntnu.idatt1002.dao.Database;
import no.ntnu.idatt1002.dao.GroupDAO;
import no.ntnu.idatt1002.dao.UserDAO;

/**
 * This class defines commonly used methods and variables
 * in all the controller classes, and aims to reduce duplicate code.
 */
public abstract class Controller {

    protected static final UserDAO userDAO = Database.getUserDAO();
    protected static final GroupDAO groupDAO = Database.getGroupDAO();
}

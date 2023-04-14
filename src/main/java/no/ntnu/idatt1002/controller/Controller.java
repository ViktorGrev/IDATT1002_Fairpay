package no.ntnu.idatt1002.controller;

import no.ntnu.idatt1002.dao.*;

/**
 * This class defines commonly used methods and variables
 * in the controller classes.
 */
abstract class Controller {

    protected static final UserDAO userDAO = Database.getDAO(UserDAO.class);
    protected static final GroupDAO groupDAO = Database.getDAO(GroupDAO.class);
    protected static final ExpenseDAO expenseDAO = Database.getDAO(ExpenseDAO.class);
    protected static final SettlementDAO settlementDAO = Database.getDAO(SettlementDAO.class);
}

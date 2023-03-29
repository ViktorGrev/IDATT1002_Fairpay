package no.ntnu.idatt1002.controller;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import no.ntnu.idatt1002.Scenes.SceneSwitcher;
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

    //General connections
    public void expenseClick(ActionEvent actionEvent) {
        SceneSwitcher.setView("expense");
    }

    public void dormGroupClick(ActionEvent actionEvent) {
        SceneSwitcher.setView("dormGroup");
    }

    public void budgetClick(ActionEvent actionEvent) {
        SceneSwitcher.setView("budget");
    }

    public void settlementClick(ActionEvent actionEvent) {
        SceneSwitcher.setView("settlement");
    }

    public void statusClick(ActionEvent actionEvent) {
        SceneSwitcher.setView("status");
    }

    public void homeClick(MouseEvent mouseEvent) {
        SceneSwitcher.setView("homepage");
    }

    public void incomeClick(ActionEvent actionEvent) {
        SceneSwitcher.setView("income");
    }

    public void profileClick(ActionEvent actionEvent) {
        SceneSwitcher.setView("profile");
    }
}

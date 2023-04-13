package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.User;
import no.ntnu.idatt1002.scene.SceneSwitcher;

abstract class MenuController extends Controller {

    @FXML
    private void homeClick() {
        SceneSwitcher.setView("homepage");
    }

    /**
     * Send the user to the expense page.
     */
    @FXML
    private void profileClick() {
        SceneSwitcher.setView("profile");
    }

    /**
     * Send the user to the expense page.
     */
    @FXML
    private void expenseButtonClick() {
        SceneSwitcher.setView("expense");
    }

    /**
     * Send the user to the settlement page.
     */
    @FXML
    private void settlementButtonClick() {
        SceneSwitcher.setView("settlement");
    }

    /**
     * Send the user to the status page.
     */
    @FXML
    private void statusButtonClick() {
        SceneSwitcher.setView("status");
    }

    /**
     * Send the user to the budget page.
     */
    @FXML
    private void budgetClick() {
        SceneSwitcher.setView("budget");
    }

    /**
     * Send the user to the dorm group page.
     */
    @FXML
    private void dormGroupClick() {
        SceneSwitcher.setView("dormGroup");
    }

    /**
     * Send the user to the income page.
     */
    @FXML
    private void incomeClick() {
        SceneSwitcher.setView("income");
    }

    /**
     * Send the user to the login page.
     */
    @FXML
    private void logoutButtonClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to logout!");
        alert.setContentText("Do you wish to logout?");

        if(alert.showAndWait().get() == ButtonType.OK){
            Group.setCurrent(null);
            User.setCurrent(null);
            SceneSwitcher.setView("login");
        }
    }
}

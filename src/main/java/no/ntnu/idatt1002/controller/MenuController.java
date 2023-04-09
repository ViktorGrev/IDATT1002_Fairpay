package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
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
}

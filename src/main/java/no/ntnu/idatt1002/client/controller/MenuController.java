package no.ntnu.idatt1002.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import no.ntnu.idatt1002.model.Group;
import no.ntnu.idatt1002.model.User;
import no.ntnu.idatt1002.client.view.Page;

/**
 * All pages that include the sidebar derives from this class.
 */
abstract class MenuController extends Controller {

  /**
   * Send the user to the home page.
   */
  @FXML
  private void homeClick() {
    viewPage(Page.HOMEPAGE);
  }

  /**
   * Send the user to the expense page.
   */
  @FXML
  private void profileClick() {
    viewPage(Page.PROFILE);
  }

  /**
   * Send the user to the expense page.
   */
  @FXML
  private void expensesClick() {
    viewPage(Page.EXPENSE);
  }

  /**
   * Send the user to the settlement page.
   */
  @FXML
  private void settlementButtonClick() {
    viewPage(Page.SETTLEMENT);
  }

  /**
   * Send the user to the status page.
   */
  @FXML
  private void statusButtonClick() {
    viewPage(Page.STATUS);
  }

  /**
   * Send the user to the budget page.
   */
  @FXML
  private void budgetClick() {
    viewPage(Page.BUDGET);
  }

  /**
   * Send the user to the group page.
   */
  @FXML
  private void dormGroupClick() {
    viewPage(Page.GROUP);
  }

  /**
   * Send the user to the income page.
   */
  @FXML
  private void incomeClick() {
    viewPage(Page.INCOME);
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
      Group.setCurrent(-1);
      User.setCurrent(-1);
      viewPage(Page.LOGIN);
    }
  }
}

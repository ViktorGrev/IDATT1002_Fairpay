package no.ntnu.idatt1002.controller;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import no.ntnu.idatt1002.Scenes.SceneSwitcher;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.User;

import java.net.URL;
import java.util.ResourceBundle;

public final class DormGroupController extends Controller implements Initializable {

  // TODO: Make super class for the controllers with the menu to remove duplicate code

  @FXML private TableView<TableUser> memberTable;

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
  private void privateSettlementClick() {
    SceneSwitcher.setView("settlement");
  }

  /**
   * Send the user to the status page.
   */
  @FXML
  private void statusOnClick() {
    SceneSwitcher.setView("status");
  }

  /**
   * Send the user to the dorm group page.
   */
  @FXML
  private void dormGroupClick() {
    SceneSwitcher.setView("dormGroup");
  }

  /**
   * Send the user to the homepage page.
   */
  @FXML
  private void homeClick() {
    SceneSwitcher.setView("homepage");
  }

  /**
   * Send the user to the budget page.
   */
  @FXML
  private void budgetClick() {
    SceneSwitcher.setView("budget");
  }

  /**
   * Send the user to the income page.
   */
  @FXML
  private void incomeClick() {
    SceneSwitcher.setView("income");
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // Create the table columns
    TableColumn<TableUser, String> usernameCol = new TableColumn<>("Username");
    usernameCol.setMinWidth(100);
    usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

    TableColumn<TableUser, String> phoneNumberCol = new TableColumn<>("Phone number");
    phoneNumberCol.setMinWidth(100);
    phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

    memberTable.getColumns().add(usernameCol);
    memberTable.getColumns().add(phoneNumberCol);

    // Add the members of the group to the table view
    Group group = Group.CURRENT;
    for(User user : group.getMembers()) {
      memberTable.getItems().add(new TableUser(user.getUsername(), user.getPhoneNumber()));
    }
  }

  // Used as a table object.
  private static class TableUser {

    private final SimpleStringProperty username;
    private final SimpleLongProperty phoneNumber;

    private TableUser(String username, long phoneNumber) {
      this.username = new SimpleStringProperty(username);
      this.phoneNumber = new SimpleLongProperty(phoneNumber);
    }

    public SimpleStringProperty usernameProperty() {
      return username;
    }

    public SimpleLongProperty phoneNumberProperty() {
      return phoneNumber;
    }
  }
}

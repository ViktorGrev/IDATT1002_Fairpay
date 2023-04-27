package no.ntnu.idatt1002.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import no.ntnu.idatt1002.model.Group;
import no.ntnu.idatt1002.client.view.Page;
import no.ntnu.idatt1002.client.view.SceneSwitcher;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller class for the group settings page.
 */
public class GroupSettingsController extends MenuController implements Initializable {

  @FXML private TextField groupNameField;
  @FXML private Label errorBox;
  @FXML private Label infoBox;

  /**
   * Attempt to update the values for a group.
   */
  @FXML
  private void updateDormGroupClick() {
    try {
      String name = groupNameField.getText();
      groupDAO.setName(Group.CURRENT, name);
      displayInfo("Group settings updated");
    } catch (Exception e) {
      displayError(e.getLocalizedMessage());
    }
  }

  /**
   * Displays the error box with the message.
   *
   * @param   message the message
   */
  private void displayError(String message) {
    errorBox.setText(message);
    errorBox.setVisible(true);
    infoBox.setVisible(false);
  }

  /**
   * Displays the info box with the message.
   *
   * @param   message the message
   */
  private void displayInfo(String message) {
    infoBox.setText(message);
    infoBox.setVisible(true);
    errorBox.setVisible(false);
  }

  /**
   * Send the user back to the group page.
   */
  @FXML
  private void goBackClick() {
    SceneSwitcher.setView(Page.GROUP);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Group group = groupDAO.find(Group.CURRENT);
    groupNameField.setText(group.getName());
  }
}

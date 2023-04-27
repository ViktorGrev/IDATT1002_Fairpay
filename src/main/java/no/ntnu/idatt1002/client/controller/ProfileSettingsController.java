package no.ntnu.idatt1002.client.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import no.ntnu.idatt1002.model.User;
import no.ntnu.idatt1002.client.view.Page;

/**
 * The controller for the profile settings page.
 */
public class ProfileSettingsController extends MenuController implements Initializable {

  @FXML private TextField usernameField;
  @FXML private TextField numberField;
  @FXML private Label errorBox;
  @FXML private Label infoBox;

  /**
   * Attempt to update the profile settings.
   */
  @FXML
  private void updateProfileClick() {
    try {
      User user = userDAO.find(User.CURRENT);
      String name = usernameField.getText();
      userDAO.setName(User.CURRENT, name);

      String numberText = numberField.getText();
      if (numberText == null || numberText.isBlank()) {
        displayError("Phone number cannot be blank");
        return;
      }

      long number = Long.parseLong(numberText);
      if (user.getPhoneNumber() != number) {
        userDAO.setPhoneNumber(User.CURRENT, number);
      }

      displayInfo("Your profile has been updated");
    } catch (Exception e) {
      displayError(e.getLocalizedMessage());
    }
  }

  /**
   * Display the error box with a message
   * @param   message the message
   */
  private void displayError(String message) {
    errorBox.setText(message);
    errorBox.setVisible(true);
    infoBox.setVisible(false);
  }

  /**
   * Display the info box with a message
   * @param   message the message
   */
  private void displayInfo(String message) {
    infoBox.setText(message);
    infoBox.setVisible(true);
    errorBox.setVisible(false);
  }

  /**
   * Send the user back to the profile page.
   */
  @FXML
  private void goBackClick() {
    viewPage(Page.PROFILE);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    User user = userDAO.find(User.CURRENT);
    usernameField.setText(user.getUsername());
    usernameField.positionCaret(user.getUsername().length());
    numberField.setText(String.valueOf(user.getPhoneNumber()));
    numberField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        numberField.setText(newValue.replaceAll("[^\\d]", ""));
      }
    });
  }
}

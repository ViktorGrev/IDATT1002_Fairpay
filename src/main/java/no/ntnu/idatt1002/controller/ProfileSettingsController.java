package no.ntnu.idatt1002.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.User;
import no.ntnu.idatt1002.scene.SceneSwitcher;

public class ProfileSettingsController extends MenuController implements Initializable {

  @FXML private TextField usernameField;
  @FXML private TextField numberField;
  @FXML private Label errorBox;
  @FXML private Label infoBox;

  @FXML
  private void updateProfileClick() {
    User user = userDAO.find(User.CURRENT);

    String name = usernameField.getText();
    if(name == null || name.isBlank()) {
      displayError("Name cannot be blank");
      return;
    }

    if(!user.getUsername().equals(name)) {
      user.setUsername(name);
      userDAO.setName(user.getId(), name);
    }

    String numberText = numberField.getText();
    if(numberText == null || numberText.isBlank()) {
      displayError("Phone number cannot be blank");
      return;
    }

    long number = Long.parseLong(numberText);
    if(user.getPhoneNumber() != number) {
      user.setPhoneNumber(number);
      userDAO.setPhoneNumber(user.getId(), number);
    }

    displayInfo("Your profile has been updated");
  }

  private void displayError(String message) {
    errorBox.setText(message);
    errorBox.setVisible(true);
    infoBox.setVisible(false);
  }

  private void displayInfo(String message) {
    infoBox.setText(message);
    infoBox.setVisible(true);
    errorBox.setVisible(false);
  }

  @FXML
  private void goBackClick() {
    SceneSwitcher.setView("profile");
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

package no.ntnu.idatt1002.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.User;
import no.ntnu.idatt1002.scene.SceneSwitcher;

public class ProfileSettingsController extends MenuController implements Initializable {

  @FXML private TextField usernameField;
  @FXML private Text nameFeedbackField;
  @FXML private TextField numberField;
  @FXML private Text numberFeedbackField;

  @FXML
  private void updateProfileClick() {
    User user = User.CURRENT;

    String name = usernameField.getText();
    if(name == null || name.isBlank()) {
      nameFeedbackField.setText("Name cannot be blank");
      return;
    }

    if(!user.getUsername().equals(name)) {
      user.setUsername(name);
      userDAO.setName(user.getId(), name);
    }

    String numberText = numberField.getText();
    if(numberText == null || numberText.isBlank()) {
      numberFeedbackField.setText("Phone number cannot be blank");
      return;
    }

    long number = Long.parseLong(numberText);
    if(user.getPhoneNumber() != number) {
      user.setPhoneNumber(number);
      userDAO.setPhoneNumber(user.getId(), number);
    }

    SceneSwitcher.setView("profile");
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    User user = User.CURRENT;
    usernameField.setText(user.getUsername());
    numberField.setText(String.valueOf(user.getPhoneNumber()));
  }
}

package no.ntnu.idatt1002.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.model.User;
import no.ntnu.idatt1002.client.view.Page;
import no.ntnu.idatt1002.util.DateUtil;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The controller class for the profile page.
 */
public final class ProfileController extends MenuController implements Initializable {

  @FXML private Text nameField;
  @FXML private Text phoneField;
  @FXML private Text registerDateField;

  /**
   * Send the user to the profile settings page.
   */
  @FXML
  private void profileSettingsClick() {
    viewPage(Page.PROFILE_SETTINGS);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    User user = userDAO.find(User.CURRENT);
    nameField.setText(user.getUsername());
    phoneField.setText(String.valueOf(user.getPhoneNumber()));
    registerDateField.setText(DateUtil.format(user.getRegisterDate().getTime(), "dd MMM yyyy"));
  }
}

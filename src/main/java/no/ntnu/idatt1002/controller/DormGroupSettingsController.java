package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.scene.SceneSwitcher;

import java.net.URL;
import java.util.ResourceBundle;

public class DormGroupSettingsController extends MenuController implements Initializable {

  @FXML private TextField groupNameField;
  @FXML private Label errorBox;
  @FXML private Label infoBox;

  @FXML
  private void updateDormGroupClick() {
    String name = groupNameField.getText();
    if(name == null || name.isBlank()) {
      displayError("Name cannot be blank");
      return;
    }
    Group group = groupDAO.find(Group.CURRENT);
    if(!group.getName().equals(name)) {
      group.setName(name);
      groupDAO.setName(group.getId(), name);
    }
    displayInfo("Group settings updated");
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
    SceneSwitcher.setView("dormgroup");
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Group group = groupDAO.find(Group.CURRENT);
    groupNameField.setText(group.getName());
  }
}

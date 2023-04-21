package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.scene.SceneSwitcher;

import java.net.URL;
import java.util.ResourceBundle;

public class DormGroupSettingsController extends MenuController implements Initializable {

  @FXML private TextField groupNameField;
  @FXML private Text feedbackField;

  @FXML
  private void updateDormGroupClick() {
    String name = groupNameField.getText();
    if(name == null || name.isBlank()) {
      feedbackField.setText("Name cannot be blank");
      return;
    }
    Group group = Group.CURRENT;
    if(!group.getName().equals(name)) {
      group.setName(name);
      groupDAO.setName(group.getId(), name);
    }
    SceneSwitcher.setView("dormgroup");
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Group group = Group.CURRENT;
    groupNameField.setText(group.getName());
  }
}

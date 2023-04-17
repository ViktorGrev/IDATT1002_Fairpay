package no.ntnu.idatt1002.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import no.ntnu.idatt1002.scene.SceneSwitcher;

public class ProfileSettingsController extends MenuController {

  @FXML
  private void updateProfileClick() {
    SceneSwitcher.setView("profile");
  }
}

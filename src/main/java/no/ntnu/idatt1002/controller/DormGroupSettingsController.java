package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import no.ntnu.idatt1002.scene.SceneSwitcher;

public class DormGroupSettingsController extends MenuController{
  @FXML
  private void updateDormGroupClick() {
    SceneSwitcher.setView("dormgroup");
  }
}

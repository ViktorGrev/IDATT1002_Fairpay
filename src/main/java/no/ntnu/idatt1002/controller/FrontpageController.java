package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import no.ntnu.idatt1002.scene.SceneSwitcher;

public final class FrontpageController {

  @FXML
  private void loginButtonClick() {
    SceneSwitcher.setView("login");
  }
}

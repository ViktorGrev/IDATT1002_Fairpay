package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import no.ntnu.idatt1002.scene.SceneSwitcher;

public final class IncomeController extends MenuController {

  @FXML
  private void newIncomeClick() {
    SceneSwitcher.setView("newIncome");
  }
}

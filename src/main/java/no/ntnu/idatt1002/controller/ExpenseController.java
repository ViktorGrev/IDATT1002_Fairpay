package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import no.ntnu.idatt1002.scene.SceneSwitcher;

public final class ExpenseController extends MenuController {

  @FXML
  private void addExpenseClick() {
    SceneSwitcher.setView("addExpense");
  }
}

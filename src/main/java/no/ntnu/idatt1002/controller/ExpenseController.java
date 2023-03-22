package no.ntnu.idatt1002.controller;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import no.ntnu.idatt1002.SceneSwitcher;

import java.io.IOException;

public class ExpenseController {

  public void testButtonClick(MouseEvent mouseEvent) throws IOException {
    SceneSwitcher.setView("test");
  }

  public void settlementButtonClicked(MouseEvent mouseEvent) throws IOException {
    SceneSwitcher.setView("test");
  }

  public void addExpenseClick(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.setView("addExpense");
  }
}

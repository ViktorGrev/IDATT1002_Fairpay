package no.ntnu.idatt1002.controller;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import no.ntnu.idatt1002.Scenes.SceneSwitcher;

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

  public void statusOnClick(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.setView("status");
  }

  public void privateSettlementClick(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.setView("settlement");
  }

  public void dormGroupClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("dormGroup");
  }

  public void budgetClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("budget");
  }

  public void homeClick(MouseEvent mouseEvent) {
    SceneSwitcher.setView("home");
  }

  public void incomeClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("income");
  }
}

package no.ntnu.idatt1002.controller;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import no.ntnu.idatt1002.Scenes.SceneSwitcher;

import java.io.IOException;

public class NewSettlementController {
  public void addExpenseClick(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.setView("addExpense");
  }

  public void removeExpenseClick(ActionEvent actionEvent) {
  }

  public void groupExpenseClick(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.setView("expense");
  }

  public void dormGroupClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("dormGroup");
  }

  public void budgetClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("budget");
  }

  public void settlementClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("settlement");
  }

  public void statusClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("status");
  }

  public void homeClick(MouseEvent mouseEvent) {
    SceneSwitcher.setView("homepage");
  }

  public void incomeClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("income");
  }
}

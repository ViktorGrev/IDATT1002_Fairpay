package no.ntnu.idatt1002.controller;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import no.ntnu.idatt1002.Scenes.SceneSwitcher;

public class NewIncomeController {
  public void homeClick(MouseEvent mouseEvent) {
    SceneSwitcher.setView("homepage");
  }

  public void profileClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("profile");
  }

  public void dormGroupClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("dormGroup");
  }

  public void groupExpenseClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("expense");
  }

  public void incomeClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("income");
  }

  public void budgetClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("budget");
  }

  public void settlementClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("dormGroup");
  }

  public void statusClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("status");
  }

  public void newIncomeConfirmClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("income");
  }
}

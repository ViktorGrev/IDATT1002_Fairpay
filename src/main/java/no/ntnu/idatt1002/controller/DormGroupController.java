package no.ntnu.idatt1002.controller;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import no.ntnu.idatt1002.Scenes.SceneSwitcher;

public class DormGroupController {
  public void expenseButtonClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("expense");
  }

  public void privateSettlementClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("settlement");
  }

  public void statusOnClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("status");
  }

  public void dormGroupClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("dormGroup");
  }

  public void homeClick(MouseEvent mouseEvent) {
    SceneSwitcher.setView("homepage");
  }

  public void budgetClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("budget");
  }

  public void incomeClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("income");
  }
}

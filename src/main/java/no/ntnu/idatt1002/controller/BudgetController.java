package no.ntnu.idatt1002.controller;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import no.ntnu.idatt1002.Scenes.SceneSwitcher;

import java.io.IOException;

public class BudgetController {

  public void dormGroupClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("dormGroup");
  }

  public void expenseClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("expense");
  }

  public void settlementClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("settlement");
  }

  public void statusClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("status");
  }

  public void incomeClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("income");
  }

  public void homeClick(MouseEvent mouseEvent) {
    SceneSwitcher.setView("homepage");
  }
}

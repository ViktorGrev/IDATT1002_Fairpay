package no.ntnu.idatt1002.controller;

import javafx.event.ActionEvent;
import no.ntnu.idatt1002.Scenes.SceneSwitcher;

import java.io.IOException;

public class SettlementController {
  public void newSettlementClick(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.setView("newSettlement");
  }

  public void groupExpenseClick(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.setView("expense");
  }

  public void statusButtonClicked(ActionEvent actionEvent) throws IOException{
    SceneSwitcher.setView("status");
  }

  public void budgetClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("budget");
  }

  public void dormGroupClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("dormGroup");
  }
}

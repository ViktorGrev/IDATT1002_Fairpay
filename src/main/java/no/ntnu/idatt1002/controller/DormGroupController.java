package no.ntnu.idatt1002.controller;

import javafx.event.ActionEvent;
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
}

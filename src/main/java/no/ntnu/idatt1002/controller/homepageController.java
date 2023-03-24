package no.ntnu.idatt1002.controller;

import javafx.event.ActionEvent;
import no.ntnu.idatt1002.SceneSwitcher;

import java.io.IOException;

public class homepageController {
  public void expenseButtonClick(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.setView("expense");
  }

  public void settlementButtonClick(ActionEvent actionEvent) throws IOException{
    SceneSwitcher.setView("settlement");
  }

  public void statusButtonClick(ActionEvent actionEvent) throws IOException{
    SceneSwitcher.setView("status");
  }
}

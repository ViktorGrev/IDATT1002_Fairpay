package no.ntnu.idatt1002.controller;

import javafx.event.ActionEvent;
import no.ntnu.idatt1002.Scenes.SceneSwitcher;

import java.io.IOException;

public class AddExpenseController {
  public void addExpenseClick(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.setView("expense");
  }
}

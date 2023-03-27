package no.ntnu.idatt1002.controller;

import javafx.event.ActionEvent;
import no.ntnu.idatt1002.Scenes.SceneSwitcher;

import java.io.IOException;

public class ExpenseController extends Controller{

  public void addExpenseClick(ActionEvent mouseEvent) throws IOException {
    SceneSwitcher.setView("addExpense");
  }
}

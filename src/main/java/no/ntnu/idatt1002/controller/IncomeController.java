package no.ntnu.idatt1002.controller;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import no.ntnu.idatt1002.Scenes.SceneSwitcher;

public class IncomeController extends Controller{
  public void newIncomeClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("newIncome");
  }
}

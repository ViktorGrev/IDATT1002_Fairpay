package no.ntnu.idatt1002.controller;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import no.ntnu.idatt1002.Scenes.SceneSwitcher;

public class NewIncomeController extends Controller{

  public void newIncomeConfirmClick(ActionEvent actionEvent) {
    SceneSwitcher.setView("income");
  }
}

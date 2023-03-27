package no.ntnu.idatt1002.controller;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import no.ntnu.idatt1002.Scenes.SceneSwitcher;

import java.io.IOException;

public class SettlementController extends Controller{
  public void newSettlementClick(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.setView("newSettlement");
  }
}

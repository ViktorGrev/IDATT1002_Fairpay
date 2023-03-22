package no.ntnu.idatt1002.controller;

import javafx.event.ActionEvent;
import no.ntnu.idatt1002.SceneSwitcher;

import java.io.IOException;

public class frontpageController {
  public void loginButtonClick(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.setView("login");
  }
}

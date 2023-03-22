package no.ntnu.idatt1002.controller;

import javafx.event.ActionEvent;
import no.ntnu.idatt1002.SceneSwitcher;

import java.io.IOException;

public class loginController {

  public void loginButtonClick(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.setView("settlement");
  }

  public void singUpButtonClick(ActionEvent actionEvent) throws IOException {
    SceneSwitcher.setView("settlement");
  }
}

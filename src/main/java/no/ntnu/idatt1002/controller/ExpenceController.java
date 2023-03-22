package no.ntnu.idatt1002.controller;

import javafx.scene.input.MouseEvent;
import no.ntnu.idatt1002.SceneSwitcher;

import java.io.IOException;

public class ExpenceController {

  public void testButtonClick(MouseEvent mouseEvent) throws IOException {
    SceneSwitcher.setView("test");
  }

  public void settlementButtonClicked(MouseEvent mouseEvent) throws IOException {
    SceneSwitcher.setView("test");
  }
}

package no.ntnu.idatt1002.controller;

import javafx.scene.input.MouseEvent;
import no.ntnu.idatt1002.SceneSwitcher;

import java.io.IOException;

public class TestController {
  public void ButtonClicked(MouseEvent mouseEvent) throws IOException {
    SceneSwitcher.setView("expense");
  }
}

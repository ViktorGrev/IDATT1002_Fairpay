package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import no.ntnu.idatt1002.SceneSwitcher;

import javax.swing.text.html.ImageView;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class SettlementController {

  public void testButtonClick(MouseEvent mouseEvent) throws IOException {
    SceneSwitcher.setView("test");
  }

  public void settlementButtonClicked(MouseEvent mouseEvent) throws IOException {
    SceneSwitcher.setView("test");
  }
}

package no.ntnu.idatt1002.client.controller;

import javafx.fxml.FXML;
import no.ntnu.idatt1002.client.view.Page;

public class HelpCategoryController extends MenuController {

  @FXML
  private void goBackClick() {
    viewPage(Page.HELP);
  }
}

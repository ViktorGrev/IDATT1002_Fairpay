package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import no.ntnu.idatt1002.scene.Page;

public final class FrontpageController extends Controller {

  @FXML
  private void loginButtonClick() {
    viewPage(Page.LOGIN);
  }
}

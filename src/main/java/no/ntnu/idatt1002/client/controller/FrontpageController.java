package no.ntnu.idatt1002.client.controller;

import javafx.fxml.FXML;
import no.ntnu.idatt1002.client.view.Page;

/**
 * The front page controller.
 */
public final class FrontpageController extends Controller {

  /**
   * Send user to login page.
   */
  @FXML
  private void loginButtonClick() {
    viewPage(Page.LOGIN);
  }
}

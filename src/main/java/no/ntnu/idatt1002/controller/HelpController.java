package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import no.ntnu.idatt1002.scene.Page;

public class HelpController extends MenuController {

  @FXML
  private void profileHelpClick() {
    viewPage(Page.PROFILE_HELP);
  }

  @FXML
  private void dormgroupHelpClick() {
    viewPage(Page.GROUP_HELP);
  }

  @FXML
  private void expenseHelpClick() {
    viewPage(Page.EXPENSE_HELP);
  }
}

package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import no.ntnu.idatt1002.scene.SceneSwitcher;

public class HelpController extends MenuController {
    @FXML
    private void profileHelpClick() {
        SceneSwitcher.setView("profileHelp");
    }

    @FXML
    private void dormgroupHelpClick() {
        SceneSwitcher.setView("dormgroupHelp");
    }

    @FXML
    private void expenseHelpClick() {
        SceneSwitcher.setView("expenseHelp");
    }
}

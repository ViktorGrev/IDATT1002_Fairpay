package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import no.ntnu.idatt1002.Scenes.SceneSwitcher;

public class JoinCreateController {

    @FXML
    private void createGroupButton() {
        SceneSwitcher.setView("createGroup");
    }
}

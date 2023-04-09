package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import no.ntnu.idatt1002.scene.SceneSwitcher;

public final class JoinCreateController extends Controller {

    /**
     * Sends a user to the page for creating a new group.
     */
    @FXML
    private void createGroupButton() {
        SceneSwitcher.setView("createGroup");
    }
}

package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import no.ntnu.idatt1002.Scenes.SceneSwitcher;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.User;

public final class CreateGroupController extends Controller {

    @FXML private TextField groupNameField;

    /**
     * Attempts to create a group with a given name, and adding
     * the user as the first member of the group, before sending
     * the user to the home page.
     */
    @FXML
    private void createGroupButtonClick() {
        try {
            String name = groupNameField.getText();
            Group group = groupDAO.create(name);
            Group.setCurrent(group);
            group.addMember(User.CURRENT);
            groupDAO.addMember(group.getId(), User.CURRENT.getId());
            SceneSwitcher.setView("homepage");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import no.ntnu.idatt1002.Scenes.SceneSwitcher;
import no.ntnu.idatt1002.dao.Database;
import no.ntnu.idatt1002.dao.GroupDAO;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.User;

public class CreateGroupController {

    private static final GroupDAO groupDAO = Database.getGroupDAO();

    @FXML private TextField groupNameField;

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

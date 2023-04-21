package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.User;
import no.ntnu.idatt1002.scene.SceneSwitcher;

public final class JoinCreateController extends MenuController {

    /**
     * Sends the user to the group invites page.
     */
    @FXML
    private void groupInvitesButton() {
        SceneSwitcher.setView("groupInvites");
    }

    /**
     * Attempts to create a group with a given name, and adding
     * the user as the first member of the group, before sending
     * the user to the home page.
     */
    @FXML
    private void createGroupButton() {
        User user = getUser(User.CURRENT);
        String name = user.getUsername() + "'s group";
        Group group = groupDAO.create(name);
        Group.setCurrent(group.getId());
        group.addMember(user);
        groupDAO.addMember(group.getId(), user.getId());
        SceneSwitcher.setView("homepage");
    }
}

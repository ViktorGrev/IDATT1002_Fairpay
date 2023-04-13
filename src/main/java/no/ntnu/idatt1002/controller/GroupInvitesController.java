package no.ntnu.idatt1002.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.Invite;
import no.ntnu.idatt1002.data.User;
import no.ntnu.idatt1002.scene.SceneSwitcher;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public final class GroupInvitesController extends Controller implements Initializable {

    @FXML private TableView<TableInvite> inviteTable;

    @FXML
    private void backButtonClick() {
        SceneSwitcher.setView("joincreatepage");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<TableInvite, String> usernameCol1 = new TableColumn<>("Group");
        usernameCol1.setCellValueFactory(new PropertyValueFactory<>("group"));

        TableColumn<TableInvite, String> acceptCol = new TableColumn<>("");
        acceptCol.setCellValueFactory(new PropertyValueFactory<>("acceptButton"));

        TableColumn<TableInvite, String> denyCol = new TableColumn<>("");
        denyCol.setCellValueFactory(new PropertyValueFactory<>("denyButton"));

        inviteTable.getColumns().addAll(Arrays.asList(usernameCol1, acceptCol, denyCol));

        List<Invite> invites = groupDAO.getInvitesByUser(User.CURRENT.getId());
        if(!invites.isEmpty()) {
            List<Long> groupIds = invites.stream().map(Invite::getGroupId).toList();
            Map<Long, Group> groups = groupDAO.find(groupIds).stream().collect(Collectors.toMap(Group::getId, group -> group));
            for(Invite invite : invites) {
                inviteTable.getItems().add(new TableInvite(
                        groups.get(invite.getGroupId()).getName(),
                        invite.getGroupId()));
            }
        } else {
            inviteTable.setVisible(false);
        }
    }

    public class TableInvite {

        private final SimpleStringProperty group;
        private final Button acceptButton;
        private final Button denyButton;

        private TableInvite(String group, long groupId) {
            this.group = new SimpleStringProperty(group);
            long userId = User.CURRENT.getId();

            this.acceptButton = new Button("Accept");
            this.acceptButton.setStyle("-fx-background-color: #e1e0e0; -fx-background-radius: 1em; -fx-border-radius: 1em; -fx-border-color: black");
            this.acceptButton.setOnMouseClicked(event -> {
                groupDAO.removeInvite(groupId, userId);
                groupDAO.addMember(groupId, userId);
                Group g = groupDAO.find(groupId);
                Group.setCurrent(g);
                SceneSwitcher.setView("homepage");
            });

            this.denyButton = new Button("Deny");
            this.denyButton.setOnMouseClicked(event -> {
                groupDAO.removeInvite(groupId, userId);
                inviteTable.getItems().removeIf(i -> i.getGroup().equals(group));
                if(inviteTable.getItems().isEmpty()) inviteTable.setVisible(false);
            });
        }

        public String getGroup() {
            return group.get();
        }

        public Button getAcceptButton() {
            return acceptButton;
        }

        public Button getDenyButton() {
            return denyButton;
        }
    }
}

package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.Invite;
import no.ntnu.idatt1002.data.User;
import no.ntnu.idatt1002.scene.Page;
import no.ntnu.idatt1002.util.DateUtil;
import no.ntnu.idatt1002.util.TableEditor;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public final class GroupController extends MenuController implements Initializable {

  @FXML private TextField inviteNameField;
  @FXML private Text inviteFeedback;
  @FXML private Label groupName;
  @FXML private TableView<User> memberTable;
  @FXML private TableView<Invite> inviteTable;

  @FXML
  private void onLeaveGroup() {
    groupDAO.removeMember(Group.CURRENT, User.CURRENT);
    Group.setCurrent(-1);
    viewPage(Page.JOIN_CREATE);
  }

  @FXML
  private void onSendInvite() {
    String name = inviteNameField.getText();
    if(name == null || name.isBlank()) return;
    Group group = groupDAO.findByUser(User.CURRENT);
    User user = getUser(name);
    if(user == null) {
      inviteFeedback.setText("That user does not exist");
    } else if(user.getId() == User.CURRENT) {
      inviteFeedback.setText("You cannot invite yourself");
      inviteNameField.setText("");
    } else {
      try {
        if(group.isMember(user.getId())) {
          inviteFeedback.setText("That user is already a member");
          return;
        }
        Invite invite = groupDAO.addInvite(group.getId(), User.CURRENT, user.getId());
        inviteTable.getItems().add(invite);
        inviteNameField.setText("");
        inviteFeedback.setText("Invite sent to " + user.getUsername());
      } catch (Exception e) {
        inviteFeedback.setText(e.getLocalizedMessage());
      }
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Group group = groupDAO.findByUser(User.CURRENT);
    groupName.setText(group.getName());

    new TableEditor<>(memberTable)
            .addColumn("Username", User::getUsername)
            .addColumn("Phone number", User::getPhoneNumber)
            .addColumn("Last login", u -> DateUtil.format(u.getLastLogin().getTime(), "E dd MMM yyyy HH:mm"))
            .addColumn("Join date", u -> DateUtil.format(u.getRegisterDate().getTime(), "dd MMM yyyy"))
            .addRows(group.getMembers());

    List<Invite> invites = groupDAO.getInvites(Group.CURRENT);

    TableEditor<Invite> inviteTableEditor = new TableEditor<>(inviteTable)
            .setPlaceholder("No invites")
            .addColumn("Username", i -> getUser(i.getTargetId()).getUsername())
            .addColumn("Invited by", i -> getUser(i.getSenderId()).getUsername())
            .addColumn(this::createCancelButton);

    if(!invites.isEmpty()) {
      List<Long> userIds = invites.stream().map(Invite::getTargetId).collect(Collectors.toList());
      userIds.addAll(invites.stream().map(Invite::getSenderId).toList());
      getUsers(userIds);
      inviteTableEditor.addRows(invites);
    }
  }

  private Button createCancelButton(Invite invite) {
    Button button = new Button("Cancel");
    button.getStyleClass().add("button2");
    button.setOnMouseClicked(event -> {
      groupDAO.removeInvite(Group.CURRENT, invite.getTargetId());
      inviteTable.getItems().removeIf(r -> invite.getTargetId() == r.getTargetId());
    });
    return button;
  }

  @FXML
  public void dormGroupSettingsClick() {
    viewPage(Page.GROUP_SETTINGS);
  }
}

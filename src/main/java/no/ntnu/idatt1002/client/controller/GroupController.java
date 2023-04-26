package no.ntnu.idatt1002.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.model.Group;
import no.ntnu.idatt1002.model.Invite;
import no.ntnu.idatt1002.model.User;
import no.ntnu.idatt1002.client.view.Page;
import no.ntnu.idatt1002.util.DateUtil;
import no.ntnu.idatt1002.util.TableEditor;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * The controller class for the group page.
 */
public final class GroupController extends MenuController implements Initializable {

  @FXML private TextField inviteNameField;
  @FXML private Text inviteFeedback;
  @FXML private Label groupName;
  @FXML private TableView<User> memberTable;
  @FXML private TableView<Invite> inviteTable;

  /**
   * Opens an alert to the user and kicks them from the
   * group if they confirm.
   */
  @FXML
  private void onLeaveGroup() {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Leave dorm group");
    alert.setHeaderText("You are about to leave the dorm group!");
    alert.setContentText("Do you wish to leave the group?");

    if(alert.showAndWait().get() == ButtonType.OK){
      groupDAO.removeMember(Group.CURRENT, User.CURRENT);
      Group.setCurrent(-1);
      viewPage(Page.JOIN_CREATE);
    }
  }

  /**
   * Attempts to send an invitation to a user.
   */
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

  /**
   * Creates a cancel invite button.
   *
   * @param   invite the invite to cancel
   * @return  the cancel button
   */
  private Button createCancelButton(Invite invite) {
    Button button = new Button("Cancel");
    button.getStyleClass().add("button2");
    button.setOnMouseClicked(event -> {
      groupDAO.removeInvite(Group.CURRENT, invite.getTargetId());
      inviteTable.getItems().removeIf(r -> invite.getTargetId() == r.getTargetId());
    });
    return button;
  }

  /**
   * Send the user to the group settings page.
   */
  @FXML
  public void dormGroupSettingsClick() {
    viewPage(Page.GROUP_SETTINGS);
  }
}

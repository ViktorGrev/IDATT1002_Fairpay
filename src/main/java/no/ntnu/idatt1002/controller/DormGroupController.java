package no.ntnu.idatt1002.controller;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.Invite;
import no.ntnu.idatt1002.data.User;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public final class DormGroupController extends MenuController implements Initializable {

  @FXML private TextField inviteNameField;
  @FXML private Text inviteFeedback;
  @FXML private TableView<TableUser> memberTable;
  @FXML private TableView<TableInvite> inviteTable;

  @FXML
  private void onSendInvite() {
    String name = inviteNameField.getText();
    if(name == null || name.isBlank()) return;
    Group group = groupDAO.findByUser(User.CURRENT.getId());
    User user = userDAO.find(name);
    if(user == null) {
      inviteFeedback.setText("That user does not exist");
    } else if(user.getId() == User.CURRENT.getId()) {
      inviteFeedback.setText("You cannot invite yourself");
      inviteNameField.setText("");
    } else {
      try {
        if(group.isMember(user.getId())) {
          inviteFeedback.setText("That user is already a member");
          return;
        }
        groupDAO.addInvite(group.getId(), User.CURRENT.getId(), user.getId());
        inviteTable.getItems().add(new TableInvite(user.getUsername(), user.getId(),
                User.CURRENT.getUsername()));
        inviteNameField.setText("");
        inviteFeedback.setText("Invite sent to " + user.getUsername());
      } catch (Exception e) {
        inviteFeedback.setText(e.getLocalizedMessage());
      }
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    TableColumn<TableUser, String> usernameCol = new TableColumn<>("Username");
    usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

    TableColumn<TableUser, String> phoneNumberCol = new TableColumn<>("Phone number");
    phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

    TableColumn<TableUser, String> loginCol = new TableColumn<>("Last login");
    loginCol.setCellValueFactory(new PropertyValueFactory<>("lastLogin"));

    TableColumn<TableUser, String> joinCol = new TableColumn<>("Join date");
    joinCol.setCellValueFactory(new PropertyValueFactory<>("joinDate"));

    memberTable.getColumns().addAll(Arrays.asList(usernameCol, phoneNumberCol, loginCol, joinCol));

    Group group = groupDAO.findByUser(User.CURRENT.getId());
    for(User user : group.getMembers()) {
      SimpleDateFormat format = new SimpleDateFormat("E dd MMM yyyy HH:mm");
      String date = format.format(user.getLastLogin());
      SimpleDateFormat format1 = new SimpleDateFormat("dd MMM yyyy");
      String date1 = format1.format(user.getRegisterDate());
      memberTable.getItems().add(new TableUser(user.getUsername(), user.getPhoneNumber(), date, date1));
    }


    TableColumn<TableInvite, String> usernameCol1 = new TableColumn<>("Username");
    usernameCol1.setCellValueFactory(new PropertyValueFactory<>("username"));

    TableColumn<TableInvite, String> invitedByCol = new TableColumn<>("Invited by");
    invitedByCol.setCellValueFactory(new PropertyValueFactory<>("invitedBy"));

    TableColumn<TableInvite, String> buttonCol = new TableColumn<>("");
    buttonCol.setCellValueFactory(new PropertyValueFactory<>("button"));

    inviteTable.getColumns().addAll(Arrays.asList(usernameCol1, invitedByCol, buttonCol));

    List<Invite> invites = groupDAO.getInvites(Group.CURRENT.getId());
    if(!invites.isEmpty()) {
      List<Long> userIds = invites.stream().map(Invite::getTargetId).collect(Collectors.toList());
      userIds.addAll(invites.stream().map(Invite::getSenderId).toList());
      List<User> users = userDAO.find(userIds);
      Map<Long, User> userMap = toUserMap(users);

      for(Invite invite : invites) {
        inviteTable.getItems().add(new TableInvite(
                userMap.get(invite.getTargetId()).getUsername(),
                invite.getTargetId(),
                userMap.get(invite.getSenderId()).getUsername()));
      }
    }
  }

  // Used as a table object.
  public static class TableUser {

    private final SimpleStringProperty username;
    private final SimpleLongProperty phoneNumber;
    private final SimpleStringProperty lastLogin;
    private final SimpleStringProperty joinDate;

    private TableUser(String username, long phoneNumber, String lastLogin, String joinDate) {
      this.username = new SimpleStringProperty(username);
      this.phoneNumber = new SimpleLongProperty(phoneNumber);
      this.lastLogin = new SimpleStringProperty(lastLogin);
      this.joinDate = new SimpleStringProperty(joinDate);
    }

    public SimpleStringProperty usernameProperty() {
      return username;
    }

    public SimpleLongProperty phoneNumberProperty() {
      return phoneNumber;
    }

    public SimpleStringProperty lastLoginProperty() {
      return lastLogin;
    }

    public SimpleStringProperty joinDateProperty() {
      return joinDate;
    }
  }

  public class TableInvite {

    private final SimpleStringProperty username;
    private final SimpleStringProperty invitedBy;
    private final Button button;

    private TableInvite(String username, long targetId, String sender) {
      this.username = new SimpleStringProperty(username);
      this.invitedBy = new SimpleStringProperty(sender);
      this.button = new Button("Cancel");
      this.button.getStylesheets().add("stylesheet");
      this.button.getStyleClass().add("button2");
      this.button.setOnMouseClicked(event -> {
        groupDAO.removeInvite(Group.CURRENT.getId(), targetId);
        inviteTable.getItems().removeIf(i -> i.getUsername().equals(username));
      });
    }

    public String getUsername() {
      return username.get();
    }

    public String getInvitedBy() {
      return invitedBy.get();
    }

    public Button getButton() {
      return button;
    }
  }

  private Map<Long, User> toUserMap(List<User> users) {
    Map<Long, User> map = new HashMap<>();
    for(User user : users)
      map.put(user.getId(), user);
    return map;
  }
}

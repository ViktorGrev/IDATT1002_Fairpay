package no.ntnu.idatt1002.controller;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.User;
import no.ntnu.idatt1002.data.economy.Settlement;
import no.ntnu.idatt1002.scene.SceneSwitcher;
import no.ntnu.idatt1002.util.DateUtil;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public final class SettlementController extends MenuController implements Initializable {

  static long settlementId;

  @FXML private TableView<TableSettlement> settlementTable;

  @FXML
  private void newSettlementClick() {
    TextInputDialog inputDialog = new TextInputDialog();
    inputDialog.setHeaderText("Settlement name");
    inputDialog.setTitle("Settlement name");

    Optional<String> input = inputDialog.showAndWait();
    input.ifPresent(s -> {
      if(s.isBlank()) return;
      User user = User.CURRENT;
      Settlement settlement = settlementDAO.create(s, user.getId());
      settlement.addMember(user.getId());
      settlementDAO.addMember(settlement.getId(), user.getId());
      settlementId = settlement.getId();
      SceneSwitcher.setView("editSettlement");
    });
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    settlementTable.setStyle("-fx-cursor: hand");
    settlementTable.setOnMouseClicked((MouseEvent event) -> {
      if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
        TableSettlement settlement = settlementTable.getSelectionModel().getSelectedItem();
        if(settlement == null) return;
        settlementId = settlement.settlementId;
        SceneSwitcher.setView("editSettlement");
      }
    });

    TableColumn<TableSettlement, String> userCol = new TableColumn<>("Name");
    userCol.setCellValueFactory(new PropertyValueFactory<>("name"));

    TableColumn<TableSettlement, String> membersCol = new TableColumn<>("Members");
    membersCol.setCellValueFactory(new PropertyValueFactory<>("members"));

    TableColumn<TableSettlement, Long> expenseCol = new TableColumn<>("Expenses");
    expenseCol.setCellValueFactory(new PropertyValueFactory<>("expenses"));

    TableColumn<TableSettlement, String> dateCol = new TableColumn<>("Date");
    dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

    settlementTable.getColumns().addAll(userCol, membersCol, expenseCol, dateCol);

    List<Settlement> settlements = settlementDAO.findByUser(User.CURRENT.getId());
    settlements.removeIf(Settlement::isDeleted);
    for(Settlement settlement : settlements) {
      String name = settlement.getName();
      String members = String.valueOf(settlement.getMembers().size());
      int expenses = settlement.getExpenses().size();
      String date = DateUtil.format(settlement.getDate().getTime(), "dd MMM yyyy");
      TableSettlement tableSettlement = new TableSettlement(name, members, expenses, date, settlement.getId());
      settlementTable.getItems().add(tableSettlement);
    }
  }

  public static class TableSettlement {

    private final SimpleStringProperty name;
    private final SimpleStringProperty members;
    private final SimpleLongProperty expenses;
    private final SimpleStringProperty date;
    private final long settlementId;

    private TableSettlement(String name, String members, long expenses, String date, long settlementId) {
      this.name = new SimpleStringProperty(name);
      this.members = new SimpleStringProperty(members);
      this.expenses = new SimpleLongProperty(expenses);
      this.date = new SimpleStringProperty(date);
      this.settlementId = settlementId;
    }

    public SimpleStringProperty nameProperty() {
      return name;
    }

    public SimpleStringProperty membersProperty() {
      return members;
    }

    public SimpleLongProperty expensesProperty() {
      return expenses;
    }

    public SimpleStringProperty dateProperty() {
      return date;
    }
  }
}

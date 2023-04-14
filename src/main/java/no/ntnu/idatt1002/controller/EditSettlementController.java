package no.ntnu.idatt1002.controller;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.User;
import no.ntnu.idatt1002.data.economy.Expense;
import no.ntnu.idatt1002.data.economy.Settlement;
import no.ntnu.idatt1002.scene.SceneSwitcher;
import no.ntnu.idatt1002.util.DateUtil;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public final class EditSettlementController extends MenuController implements Initializable {

  @FXML private TableView<TableExpense> expenseTable;
  @FXML private Label nameField;
  @FXML private VBox memberBox;

  @FXML
  private void addExpenseClick() {
    SceneSwitcher.setView("addSettlementExpense");
  }

  @FXML
  private void deleteSettlementClick() {
    Settlement settlement = settlementDAO.find(SettlementController.settlementId);
    settlementDAO.delete(settlement.getId());
    SceneSwitcher.setView("settlement");
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Settlement settlement = settlementDAO.find(SettlementController.settlementId);
    nameField.setText(settlement.getName());

    Group group = Group.CURRENT;

    for(User user : group.getMembers()) {
      RadioButton radioButton = new RadioButton();
      if(settlement.getMembers().contains(user.getId())) {
        radioButton.setSelected(true);
        if(user.getId() == User.CURRENT.getId()) {
          radioButton.setDisable(true);
        }
      }
      radioButton.setOnMouseClicked(e -> {
        if(user.getId() == User.CURRENT.getId()) return;
        if(radioButton.isSelected()) {
          settlementDAO.addMember(settlement.getId(), user.getId());
          settlement.addMember(user.getId());
        } else {
          settlementDAO.removeMember(settlement.getId(), user.getId());
          settlement.removeMember(user.getId());
        }
      });
      Label label = new Label(user.getUsername());
      label.setStyle("-fx-border-style: hidden hidden solid hidden; -fx-min-width: 98px; -fx-border-radius: 0em 5px; -fx-border-color:  #793f3f; -fx-text-fill:  #793f3f");
      HBox hBox = new HBox(radioButton, label);
      memberBox.getChildren().add(hBox);
    }

    TableColumn<TableExpense, String> userCol = new TableColumn<>("Name");
    userCol.setCellValueFactory(new PropertyValueFactory<>("name"));

    TableColumn<TableExpense, String> categoryCol = new TableColumn<>("Category");
    categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

    TableColumn<TableExpense, Long> priceCol = new TableColumn<>("Price");
    priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    TableColumn<TableExpense, String> dateCol = new TableColumn<>("Date");
    dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

    expenseTable.getColumns().addAll(userCol, categoryCol, priceCol, dateCol);

    if(settlement.getExpenses().isEmpty()) return;
    List<Expense> expenses = expenseDAO.find(settlement.getExpenses());
    for(Expense expense : expenses) {
      String name = expense.getName();
      String category = expense.getType().getCategoryName();
      long price = expense.getAmount().longValue();
      String date = DateUtil.format(expense.getDate().getTime(), "dd MMM yyyy");
      TableExpense tableSettlement = new TableExpense(name, category, price, date);
      expenseTable.getItems().add(tableSettlement);
    }
  }

  public static class TableExpense {

    private final SimpleStringProperty name;
    private final SimpleStringProperty category;
    private final SimpleLongProperty price;
    private final SimpleStringProperty date;

    private TableExpense(String name, String category, long price, String date) {
      this.name = new SimpleStringProperty(name);
      this.category = new SimpleStringProperty(category);
      this.price = new SimpleLongProperty(price);
      this.date = new SimpleStringProperty(date);
    }

    public SimpleStringProperty nameProperty() {
      return name;
    }

    public SimpleStringProperty categoryProperty() {
      return category;
    }

    public SimpleLongProperty priceProperty() {
      return price;
    }

    public SimpleStringProperty dateProperty() {
      return date;
    }
  }
}

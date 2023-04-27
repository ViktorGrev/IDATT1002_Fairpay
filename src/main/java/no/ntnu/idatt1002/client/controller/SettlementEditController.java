package no.ntnu.idatt1002.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import no.ntnu.idatt1002.model.Group;
import no.ntnu.idatt1002.model.User;
import no.ntnu.idatt1002.model.economy.Expense;
import no.ntnu.idatt1002.model.economy.Settlement;
import no.ntnu.idatt1002.client.view.Page;
import no.ntnu.idatt1002.util.DateUtil;
import no.ntnu.idatt1002.util.TableEditor;
import no.ntnu.idatt1002.util.UserUtil;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * The controller class for the edit settlement page.
 */
public final class SettlementEditController extends MenuController implements Initializable {

  @FXML private TableView<Expense> expenseTable;
  @FXML private Label nameField;
  @FXML private VBox memberBox;
  @FXML private Button deleteButton;
  @FXML private Button endButton;
  @FXML private BorderPane paymentPane;

  /**
   * Sends the user to the add expense page for settlements.
   */
  @FXML
  private void addExpenseClick() {
    viewPage(Page.SETTLEMENT_ADD_EXPENSE);
  }

  /**
   * Deletes the settlement and sends the user back to the main page.
   */
  @FXML
  private void deleteSettlementClick() {
    Settlement settlement = settlementDAO.find(SettlementController.settlementId);
    settlementDAO.delete(settlement.getId());
    viewPage(Page.SETTLEMENT);
  }

  /**
   * Ends the settlement and displays the results.
   */
  @FXML
  private void endSettlementClick() {
    Settlement settlement = settlementDAO.find(SettlementController.settlementId);
    settlementDAO.setEnded(settlement.getId(), true);
    settlement.setEnded(true);
    endButton.setVisible(false);
    showPayments(settlement);
  }

  /**
   * Send the user back to the main page.
   */
  @FXML
  private void goBackClick() {
    viewPage(Page.SETTLEMENT);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Settlement settlement = settlementDAO.find(SettlementController.settlementId);
    nameField.setText(settlement.getName());

    if(settlement.getUserId() != User.CURRENT) {
      deleteButton.setVisible(false);
      endButton.setVisible(false);
      memberBox.setDisable(true);
    } else {
      if(settlement.isEnded()) {
        endButton.setVisible(false);
      }
    }

    if(settlement.isEnded()) {
      showPayments(settlement);
    }

    Group group = groupDAO.find(Group.CURRENT);

    for(User user : group.getMembers()) {
      RadioButton radioButton = new RadioButton();
      if(settlement.getMembers().contains(user.getId())) {
        radioButton.setSelected(true);
        radioButton.setDisable(user.getId() == User.CURRENT);
      }
      radioButton.setStyle("-fx-cursor: hand; -fx-padding: 0px 7px 0px 0px;");
      radioButton.setOnMouseClicked(e -> {
        if(user.getId() == User.CURRENT) return;
        if(radioButton.isSelected()) {
          settlementDAO.addMember(settlement.getId(), user.getId());
          settlement.addMember(user.getId());
        } else {
          settlementDAO.removeMember(settlement.getId(), user.getId());
          settlement.removeMember(user.getId());
        }
      });
      Label label = new Label(user.getUsername());
      label.setStyle("-fx-font-size: 14px;");
      HBox hBox = new HBox(radioButton, label);
      hBox.setStyle("-fx-padding: 5px");
      memberBox.getChildren().add(hBox);
    }

    Map<Long, User> users = UserUtil.toMap(group.getMembers());

    TableEditor<Expense> expenseTableEditor = new TableEditor<>(expenseTable)
            .setPlaceholder("No expenses")
            .addColumn("Name", expense -> expense.hasName() ? expense.getName() : expense.getType().getName())
            .addColumn("Added by", expense -> users.get(expense.getUserId()).getUsername())
            .addColumn("Amount (kr)", Expense::getAmount)
            .addColumn("Date", expense -> DateUtil.format(expense.getDate().getTime(), "dd MMM yyyy"));

    if(!settlement.getExpenses().isEmpty()) {
      List<Expense> expenses = expenseDAO.find(settlement.getExpenses());
      expenseTableEditor.addRows(expenses);
    }
  }

  /**
   * Calculate and show how much each user must pay or receive.
   *
   * @param   settlement the settlement
   */
  private void showPayments(Settlement settlement) {
    expenseTable.setVisible(false);
    memberBox.setDisable(true);
    if(settlement.getExpenses().isEmpty()) return;
    List<Expense> expenses = expenseDAO.find(settlement.getExpenses());

    long total = expenses.stream().mapToLong(e -> e.getAmount().longValue()).sum();
    long each = total / settlement.getMembers().size();

    Map<Long, Long> receiveMap = new HashMap<>();
    Map<Long, Long> payMap = new HashMap<>();

    getUsers(expenses.stream().map(Expense::getUserId).toList());

    VBox vBox = new VBox();
    HBox hBox = new HBox();
    Label totalText = new Label("Total amount: ");
    totalText.setStyle("-fx-padding: 10px; -fx-font-size: 16px;");
    Label totalAmount = new Label(total + " kr");
    totalAmount.setStyle("-fx-padding: 10px; -fx-font-size: 18px;");
    totalAmount.setTextFill(Paint.valueOf("#572828"));
    hBox.getChildren().addAll(totalText, totalAmount);
    vBox.getChildren().add(hBox);
    for(long userId : settlement.getMembers()) {
      long diff = getUserAmount(userId, expenses) - each;
      Label label = null;
      if(diff < 0) {
        payMap.put(userId, Math.abs(diff));
        label = new Label(getUser(userId).getUsername() + " must pay " + Math.abs(diff));
      } else if(diff > 0) {
        receiveMap.put(userId, diff);
        label = new Label(getUser(userId).getUsername() + " must receive " + diff);
      }
      if(label == null) continue;
      label.setStyle("-fx-padding: 10px; -fx-font-size: 16px;");
      vBox.getChildren().add(label);
    }
    paymentPane.centerProperty().setValue(vBox);
  }

  /**
   * Get the total amount for a user.
   *
   * @param   userId the user ID
   * @param   expenses the list of expenses
   * @return  the total amount for a user
   */
  private static long getUserAmount(long userId, List<Expense> expenses) {
    long total = 0;
    for(Expense expense : expenses) {
      if(expense.getUserId() != userId) continue;
      total += expense.getAmount().longValue();
    }
    return total;
  }
}

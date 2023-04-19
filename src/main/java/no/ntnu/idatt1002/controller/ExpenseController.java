package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.User;
import no.ntnu.idatt1002.data.economy.Expense;
import no.ntnu.idatt1002.scene.SceneSwitcher;
import no.ntnu.idatt1002.util.DateUtil;
import no.ntnu.idatt1002.util.UserUtil;

import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ExpenseController extends MenuController implements Initializable {

  @FXML private TableView<Expense> expenseTable;
  @FXML private Text totalAmountField;
  @FXML private VBox mustPayField;

  @FXML
  private void addExpenseClick() {
    SceneSwitcher.setView("addExpense");
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    User user = User.CURRENT;
    Group group = Group.CURRENT;
    Map<Long, User> users = UserUtil.toMap(group.getMembers());

    TableEditor<Expense> expenseTableEditor = new TableEditor<>(expenseTable)
            .setPlaceholder("No expenses")
            .addColumn("Name", expense -> expense.hasName() ? expense.getName() : expense.getType().getCategoryName())
            .addColumn("Added by", expense -> users.get(expense.getUserId()).getUsername())
            .addColumn("Total amount", Expense::getAmount)
            .addColumn("Your part", expense -> expense.getAmount().longValue() / group.getMembers().size())
            .addColumn("Date", expense -> DateUtil.format(expense.getDate().getTime(), "dd MMM yyyy"))
            .addColumn("Paid", expense -> expense.getUserId() == user.getId() ? null : createPaidBox(expense));

    List<Long> expenseIds = group.getExpenses();
    if(!expenseIds.isEmpty()) {
      List<Expense> expenses = expenseDAO.find(expenseIds);
      if(!expenses.isEmpty()) {
        expenseTableEditor.addRows(expenses);
      }
    }

    calculateTotal();
  }

  private void calculateTotal() {
    User user = User.CURRENT;
    Group group = Group.CURRENT;
    int total = 0;
    Map<Long, Long> owes = new HashMap<>();

    List<Long> expenseIds = group.getExpenses();
    List<Expense> expenses = expenseDAO.find(expenseIds);
    for(Expense expense : expenses) {
      long expenseUserId = expense.getUserId();
      if(expenseUserId == user.getId()) continue;
      if(!group.isPaid(expense.getExpenseId(), user.getId())) {
        long amount = expense.getAmount().longValue() / group.getMembers().size();
        total += amount;
        if(!owes.containsKey(expenseUserId)) owes.put(expenseUserId, 0L);
        owes.put(expenseUserId, owes.get(expenseUserId) + amount);
      }
    }
    totalAmountField.setText(total + "kr");

    Map<Long, User> users = UserUtil.toMap(group.getMembers());
    mustPayField.getChildren().removeIf(node -> !(node instanceof Label));
    for(long l : owes.keySet()) {
      HBox hBox = new HBox();
      hBox.getChildren().addAll(new Label(users.get(l).getUsername() + ": "), new Label(owes.get(l) + "kr"));
      mustPayField.setStyle("-fx-font-size: 14");
      mustPayField.getChildren().add(hBox);
    }
  }

  private CheckBox createPaidBox(Expense expense) {
    long userId = User.CURRENT.getId();
    long expenseId = expense.getExpenseId();

    CheckBox checkBox = new CheckBox();
    if(Group.CURRENT.isPaid(expenseId, userId)) {
      checkBox.setSelected(true);
    }

    checkBox.setOnAction(e -> {
      if(checkBox.isSelected()) {
        groupDAO.setPaidExpense(expenseId, userId);
        Group.CURRENT.addPaidExpense(expenseId, userId);
      } else {
        groupDAO.unsetPaidExpense(expenseId, userId);
        Group.CURRENT.removePaidExpense(expenseId, userId);
      }
      calculateTotal();
    });
    return checkBox;
  }
}

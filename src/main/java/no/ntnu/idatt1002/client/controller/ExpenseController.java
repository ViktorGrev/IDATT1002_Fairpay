package no.ntnu.idatt1002.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.model.Group;
import no.ntnu.idatt1002.model.User;
import no.ntnu.idatt1002.model.economy.Expense;
import no.ntnu.idatt1002.client.view.Page;
import no.ntnu.idatt1002.util.DateUtil;
import no.ntnu.idatt1002.util.TableEditor;
import no.ntnu.idatt1002.util.UserUtil;

import java.net.URL;
import java.util.*;

/**
 * Controller class for the expense page.
 */
public final class ExpenseController extends MenuController implements Initializable {

  @FXML private TableView<Expense> expenseTable;
  @FXML private Text totalAmountField;
  @FXML private Text totalAmountReceive;

  /**
   * Send the user to the expense add page.
   */
  @FXML
  private void addExpenseClick() {
    viewPage(Page.EXPENSE_ADD);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Group group = getGroup(Group.CURRENT);
    Map<Long, User> users = UserUtil.toMap(group.getMembers());

    TableEditor<Expense> expenseTableEditor = new TableEditor<>(expenseTable)
            .setPlaceholder("No expenses")
            .addColumn("Name", expense -> expense.hasName() ? expense.getName() : expense.getType().getName())
            .addColumn("Added by", expense -> users.get(expense.getUserId()).getUsername())
            .addColumn("Amount (kr)", Expense::getAmount)
            .addColumn("Your share (kr)", expense -> expense.getAmount().longValue() / expense.getShares())
            .addColumn("Date", expense -> DateUtil.format(expense.getDate().getTime(), "dd MMM yyyy"))
            .addColumn("Status", this::createStatus)
            .addColumn(this::createDeleteButton);

    List<Long> expenseIds = group.getExpenses();
    if(!expenseIds.isEmpty()) {
      List<Expense> expenses = expenseDAO.find(expenseIds);
      if(!expenses.isEmpty()) {
        expenses.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
        expenseTableEditor.addRows(expenses);
      }
    }

    calculateTotal();
  }

  /**
   * Calculates the amount to pay and receive for the user.
   */
  private void calculateTotal() {
    Group group = getGroup(Group.CURRENT);
    int total = 0;
    int totalReceive = 0;
    Map<Long, Long> owes = new HashMap<>();

    List<Long> expenseIds = group.getExpenses();
    if(!expenseIds.isEmpty()) {
      List<Expense> expenses = expenseDAO.find(expenseIds);
      for(Expense expense : expenses) {
        long expenseUserId = expense.getUserId();
        if(expenseUserId == User.CURRENT) continue;
        if(!group.isPaid(expense.getId(), User.CURRENT)) {
          long amount = expense.getAmount().longValue() / group.getMembers().size();
          total += amount;
          if(!owes.containsKey(expenseUserId)) owes.put(expenseUserId, 0L);
          owes.put(expenseUserId, owes.get(expenseUserId) + amount);
        }
      }
      for(Expense expense : expenses) {
        if(User.CURRENT == expense.getUserId()) {
          long paidCount = group.getPaidExpenses().getOrDefault(expense.getId(), new ArrayList<>()).size();
          int totalPaid = expense.getShares() - 1;
          if(paidCount == totalPaid) continue;
          long amount = expense.getAmount().longValue() / expense.getShares();
          totalReceive += amount;
        }
      }
    }
    totalAmountField.setText(total + " kr");
    totalAmountReceive.setText(totalReceive + " kr");
  }

  /**
   * Creates a checkbox if the expense is not the users expense.
   * If it is, it will return a display of how many have received their
   * share, or a check mark if everyone received their share.
   * @param   expense the expense
   * @return  the status object
   */
  private Object createStatus(Expense expense) {
    Group group = getGroup(Group.CURRENT);
    long userId = User.CURRENT;
    long expenseId = expense.getId();

    if(userId == expense.getUserId()) {
      long receivedCount = group.getPaidExpenses().getOrDefault(expenseId, new ArrayList<>()).size();
      int total = expense.getShares() - 1;
      if(receivedCount == total) {
        Image image = new Image(ExpenseController.class.getResourceAsStream("/image/check.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        return imageView;
      }
      return new Label(receivedCount + "/" + total + " received");
    }

    CheckBox checkBox = new CheckBox("Paid");
    checkBox.setTooltip(new Tooltip("Mark as paid"));
    if(group.isPaid(expenseId, userId)) {
      checkBox.setSelected(true);
    }

    checkBox.setOnAction(e -> {
      if(checkBox.isSelected()) {
        groupDAO.setPaidExpense(expenseId, userId);
        group.addPaidExpense(expenseId, userId);
      } else {
        groupDAO.unsetPaidExpense(expenseId, userId);
        group.removePaidExpense(expenseId, userId);
      }
      calculateTotal();
    });
    return checkBox;
  }

  /**
   * Creates the delete button.
   *
   * @param   expense the expense to delete
   * @return  a button
   */
  private Button createDeleteButton(Expense expense) {
    if(expense.getUserId() != User.CURRENT) return null;
    Button button = new Button();
    Image image = new Image(ExpenseController.class.getResourceAsStream("/image/delete_dark.png"));
    ImageView imageView = new ImageView(image);
    imageView.setFitHeight(20);
    imageView.setFitWidth(20);
    button.getStyleClass().add("deleteButton");
    button.setGraphic(imageView);
    button.setTooltip(new Tooltip("Delete expense"));
    button.setOnMouseClicked(event -> {
      groupDAO.removeExpense(Group.CURRENT, expense.getId());
      expenseDAO.delete(expense.getId());
      expenseTable.getItems().removeIf(e -> e.getId() == expense.getId());
      calculateTotal();
    });
    return button;
  }
}

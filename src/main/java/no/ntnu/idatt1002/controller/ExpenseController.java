package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.economy.Expense;
import no.ntnu.idatt1002.scene.SceneSwitcher;
import no.ntnu.idatt1002.util.DateUtil;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public final class ExpenseController extends MenuController implements Initializable {

  @FXML private TableView<Expense> expenseTable;

  @FXML
  private void addExpenseClick() {
    SceneSwitcher.setView("addExpense");
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    TableEditor<Expense> expenseTableEditor = new TableEditor<>(expenseTable)
            .setPlaceholder("No expenses")
            .addColumn("Name", expense -> expense.hasName() ? expense.getName() : expense.getType().getCategoryName())
            .addColumn("Price", Expense::getAmount)
            .addColumn("Date", expense -> DateUtil.format(expense.getDate().getTime(), "dd MMM yyyy"));

    List<Long> expenseIds = Group.CURRENT.getExpenses();
    if(!expenseIds.isEmpty()) {
      List<Expense> expenses = expenseDAO.find(expenseIds);
      if(!expenses.isEmpty()) {
        expenseTableEditor.addRows(expenses);
      }
    }
  }
}

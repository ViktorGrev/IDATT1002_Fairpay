package no.ntnu.idatt1002.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import no.ntnu.idatt1002.model.Group;
import no.ntnu.idatt1002.model.User;
import no.ntnu.idatt1002.model.economy.Expense;
import no.ntnu.idatt1002.model.economy.ExpenseType;
import no.ntnu.idatt1002.client.view.Page;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controller class for the add expense page.
 */
public final class ExpenseAddController extends MenuController implements Initializable {

  @FXML private ComboBox<String> typeField;
  @FXML private Label nameText;
  @FXML private TextField nameField;
  @FXML private TextField amountField;
  @FXML private DatePicker dateField;

  /**
   * Adds a new expense to the group.
   */
  @FXML
  private void addExpenseClick() {
    ExpenseType type = ExpenseType.fromName(typeField.getValue());
    String name = nameField.getText().isBlank() ? null : nameField.getText();
    BigDecimal amount = BigDecimal.valueOf(Long.parseLong(amountField.getText()));
    LocalDate localDate = dateField.getValue();
    Date date = java.sql.Date.valueOf(localDate);
    Group group = groupDAO.find(Group.CURRENT);
    Expense expense = expenseDAO.create(User.CURRENT, type, name, amount, date, group.getMembers().size());
    groupDAO.addExpense(group.getId(), expense.getId());
    group.addExpense(expense.getId());
    viewPage(Page.EXPENSE);
  }

  /**
   * Send the user back to the expense page.
   */
  @FXML
  private void goBackClick() {
    viewPage(Page.EXPENSE);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    typeField.setOnAction(e -> {
      if(typeField.getValue().equals("Other")) {
        nameText.setVisible(true);
        nameField.setVisible(true);
      } else {
        nameText.setVisible(false);
        nameField.setVisible(false);
      }
    });

    for(ExpenseType type : ExpenseType.values()) {
      typeField.getItems().add(type.getName());
    }

    typeField.setValue(ExpenseType.FOOD.getName());
    dateField.setValue(LocalDate.now());

    amountField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        amountField.setText(newValue.replaceAll("[^\\d]", ""));
      }
    });
  }
}

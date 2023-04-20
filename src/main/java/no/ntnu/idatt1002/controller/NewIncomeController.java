package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.User;
import no.ntnu.idatt1002.data.economy.Income;
import no.ntnu.idatt1002.scene.SceneSwitcher;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public final class NewIncomeController extends MenuController {

  @FXML private TextField nameField;
  @FXML private TextField amountField;
  @FXML private DatePicker dateField;

  @FXML
  private void newIncomeConfirmClick() {
    String name = nameField.getText().isBlank() ? null : nameField.getText();
    BigDecimal amount = BigDecimal.valueOf(Long.parseLong(amountField.getText()));
    LocalDate localDate = dateField.getValue();
    Date date = java.sql.Date.valueOf(localDate);
    Income income = incomeDAO.create(User.CURRENT.getId(), name, amount, date, Group.CURRENT.getMembers().size());
    groupDAO.addIncome(Group.CURRENT.getId(), income.getIncomeId());
    Group.CURRENT.addIncome(income.getIncomeId());
    SceneSwitcher.setView("income");
  }
}

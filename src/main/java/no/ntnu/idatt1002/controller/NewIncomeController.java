package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.User;
import no.ntnu.idatt1002.data.economy.Income;
import no.ntnu.idatt1002.scene.SceneSwitcher;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public final class NewIncomeController extends MenuController implements Initializable {

    @FXML private TextField nameField;
    @FXML private TextField amountField;
    @FXML private DatePicker dateField;

    @FXML
    private void newIncomeConfirmClick() {
        String name = nameField.getText().isBlank() ? null : nameField.getText();
        BigDecimal amount = BigDecimal.valueOf(Long.parseLong(amountField.getText()));
        LocalDate localDate = dateField.getValue();
        Date date = java.sql.Date.valueOf(localDate);
        Group group = getGroup(Group.CURRENT);
        Income income = incomeDAO.create(User.CURRENT, name, amount, date, group.getMembers().size());
        groupDAO.addIncome(group.getId(), income.getIncomeId());
        group.addIncome(income.getIncomeId());
        SceneSwitcher.setView("income");
    }

    @FXML
    private void goBackClick() {
        SceneSwitcher.setView("income");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateField.setValue(LocalDate.now());
        amountField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                amountField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
}

package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.economy.Budget;
import no.ntnu.idatt1002.data.economy.ExpenseType;
import no.ntnu.idatt1002.scene.SceneSwitcher;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public final class BudgetController extends MenuController implements Initializable {

    @FXML private TableView<ExpenseType> budgetTable;
    @FXML private BarChart<String, Long> barChart;
    @FXML private Text sum;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Budget budget = budgetDAO.find(Group.CURRENT.getId());

        TableEditor<ExpenseType> budgetTableEditor = new TableEditor<>(budgetTable)
                .addColumn("Expense type", ExpenseType::getCategoryName)
                .addColumn("Amount", type -> createInputField(type, budget.getAmount(type).longValue()));
        for(ExpenseType type : ExpenseType.values())
            budgetTableEditor.addRow(type);

        sum.setText(budget.getTotal() + "kr");

        XYChart.Series<String, Long> series1 = new XYChart.Series<>();
        series1.setName("Budget");
        for(ExpenseType expense : ExpenseType.values()) {
            long amount = budget.getAmount(expense).longValue();
            series1.getData().add(new XYChart.Data<>(expense.getCategoryName(), amount));
        }
        barChart.getData().addAll(series1);
    }

    private TextField createInputField(ExpenseType type, long amount) {
        TextField inputField = new TextField(String.valueOf(amount));
        inputField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                budgetDAO.addType(Group.CURRENT.getId(), type,
                        BigDecimal.valueOf(Long.parseLong(inputField.getText())));
                SceneSwitcher.setView("budget");
            }
        });
        return inputField;
    }
}


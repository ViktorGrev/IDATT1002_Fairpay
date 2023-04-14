package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.economy.Budget;
import no.ntnu.idatt1002.data.economy.ExpenseType;
import no.ntnu.idatt1002.scene.SceneSwitcher;

import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

public final class BudgetController extends MenuController implements Initializable {

    @FXML private TableView<BudgetItem> budgetTable;
    @FXML private BarChart barChart;
    @FXML private Text sum;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Budget budget = budgetDAO.find(Group.CURRENT.getId());

        TableColumn<BudgetItem, String> expenseCol1 = new TableColumn<>("Expense type");
        expenseCol1.setCellValueFactory(new PropertyValueFactory<>("expense"));

        TableColumn<BudgetItem, String> acceptCol = new TableColumn<>("Amount");
        acceptCol.setCellValueFactory(new PropertyValueFactory<>("acceptButton"));

        budgetTable.getColumns().addAll(Arrays.asList(expenseCol1, acceptCol));

        int s = 0;
        List<ExpenseType> expenseTypes = Arrays.asList(ExpenseType.values());
        if(!expenseTypes.isEmpty()) {
            for(ExpenseType expense : expenseTypes) {
                long amount = budget.getAmount(expense) != null ? budget.getAmount(expense).longValue() : 0;
                s += amount;
                BudgetItem tableInvite = new BudgetItem(expense, amount);
                budgetTable.getItems().add(tableInvite);
            }
        } else {
            budgetTable.setVisible(false);
        }
        sum.setText(s + "kr");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Budget");
        for(ExpenseType expense : expenseTypes) {
            long amount = budget.getAmount(expense) != null ? budget.getAmount(expense).longValue() : 0;
            series1.getData().add(new XYChart.Data(expense.getCategoryName(), amount));
        }
        barChart.getData().addAll(series1);
    }

    public class BudgetItem {
        private final ExpenseType expense;
        private final TextField acceptButton;

        private BudgetItem(ExpenseType expense, long amount) {
            this.expense = expense;
            this.acceptButton = new TextField(String.valueOf(amount));
            this.acceptButton.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    //sum += Integer.parseInt(acceptButton.getText());
                    budgetDAO.addType(Group.CURRENT.getId(), expense, BigDecimal.valueOf(Long.parseLong(acceptButton.getText())));
                    SceneSwitcher.setView("budget");
                }
            });
        }

        public String getExpense() {
            return expense.getCategoryName();
        }

        public TextField getAcceptButton() {
            return acceptButton;
        }
    }
}


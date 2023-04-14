package no.ntnu.idatt1002.controller;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.Invite;
import no.ntnu.idatt1002.data.User;
import no.ntnu.idatt1002.data.economy.ExpenseType;
import no.ntnu.idatt1002.scene.SceneSwitcher;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public final class BudgetController extends MenuController implements Initializable {

    @FXML private TableView<BudgetItem> budgetTable;
    @FXML private BarChart barChart;
    @FXML private Text sumText;
    private int sum = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<BudgetItem, String> expenseCol1 = new TableColumn<>("Expense type");
        expenseCol1.setCellValueFactory(new PropertyValueFactory<>("expense"));

        TableColumn<BudgetItem, String> acceptCol = new TableColumn<>("Amount");
        acceptCol.setCellValueFactory(new PropertyValueFactory<>("acceptButton"));

        budgetTable.getColumns().addAll(Arrays.asList(expenseCol1, acceptCol));

        List<ExpenseType> expenseTypes = Arrays.asList(ExpenseType.values());
        if(!expenseTypes.isEmpty()) {
            for(ExpenseType expense : expenseTypes) {
                BudgetItem tableInvite = new BudgetItem(expense.getCategoryName());
                budgetTable.getItems().add(tableInvite);
            }
        } else {
            budgetTable.setVisible(false);
        }

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Budget");
        for(ExpenseType expense : expenseTypes){
            series1.getData().add(new XYChart.Data(expense.getCategoryName(), 100 /*Hent budget amount*/));
        }
        barChart.getData().addAll(series1);
    }

    public class BudgetItem {
        private final String expense;
        private final TextField acceptButton;

        private BudgetItem(String expense) {
            this.expense = expense;
            this.acceptButton = new TextField();
            this.acceptButton.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    sum += Integer.parseInt(acceptButton.getText());
                    //Legg til i DAO
                }
            });
        }

        public String getExpense() {
            return expense;
        }

        public TextField getAcceptButton() {
            return acceptButton;
        }
    }
}


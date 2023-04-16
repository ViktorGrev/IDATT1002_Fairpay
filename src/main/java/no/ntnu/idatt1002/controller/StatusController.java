package no.ntnu.idatt1002.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.economy.Expense;
import no.ntnu.idatt1002.data.economy.ExpenseType;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public final class StatusController extends MenuController implements Initializable {

    @FXML
    private PieChart pieChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Expense> expenses = expenseDAO.find(Group.CURRENT.getExpenses());
        expenses.removeIf(expense -> {
            return System.currentTimeMillis() - expense.getDate().getTime() > 0;
        });
        int s = 0;
        for(Expense expense : expenses) {
            System.out.println("expense " + expense.getExpenseId() + " " + expense.getName());
            s += expense.getAmount().longValue();
        }
        System.out.println(s);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Malin", 200),
                new PieChart.Data("Viktor", 100),
                new PieChart.Data("Sommer", 100),
                new PieChart.Data("Ina", 220),
                new PieChart.Data("Henrik", 230));

        pieChartData.forEach(data ->
            data.nameProperty().bind(
                    Bindings.concat(
                            data.getName(), " amount ", data.pieValueProperty()
                    )
            )
        );

        pieChart.getData().addAll(pieChartData);
    }
}

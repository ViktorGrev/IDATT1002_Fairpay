package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.economy.Budget;
import no.ntnu.idatt1002.data.economy.Expense;
import no.ntnu.idatt1002.data.economy.ExpenseType;

import java.net.URL;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.*;
import java.util.function.ToLongFunction;

public final class StatusController extends MenuController implements Initializable {

    @FXML private BarChart barChart;
    @FXML private Label statusField;
    @FXML private Text spentSum;
    @FXML private Text budgetSum;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        String monthName = new DateFormatSymbols().getMonths()[month];
        statusField.setText("Status " + monthName + " " + calendar.get(Calendar.YEAR));

        List<Expense> expenses = getMonthlyExpenses();
        Map<ExpenseType, Long> expenseMap = new HashMap<>();
        for(Expense expense : expenses) {
            long amount = expense.getAmount().longValue();
            if(!expenseMap.containsKey(expense.getType())) expenseMap.put(expense.getType(), 0L);
            expenseMap.put(expense.getType(), expenseMap.get(expense.getType()) + amount);
        }

        XYChart.Series<String, Long> expenseSeries = new XYChart.Series<>();
        expenseSeries.setName("Expenses");
        for(ExpenseType expense : ExpenseType.values()) {
            long amount = expenseMap.getOrDefault(expense, 0L);
            expenseSeries.getData().add(new XYChart.Data<>(expense.getCategoryName(), amount));
        }

        Budget budget = budgetDAO.find(Group.CURRENT.getId());
        XYChart.Series<String, Long> budgetSeries = new XYChart.Series<>();
        budgetSeries.setName("Budget");
        for(ExpenseType expense : ExpenseType.values()) {
            long amount = budget.getAmount(expense) != null ? budget.getAmount(expense).longValue() : 0;
            budgetSeries.getData().add(new XYChart.Data<>(expense.getCategoryName(), amount));
        }
        barChart.getData().addAll(expenseSeries, budgetSeries);

        spentSum.setText(expenses.stream().mapToLong(value -> value.getAmount().longValue()).sum() + "kr");
        budgetSum.setText(budget.getTotal() + "kr");

        /*ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
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

        pieChart.getData().addAll(pieChartData);*/
    }

    private List<Expense> getMonthlyExpenses() {
        List<Expense> expenses = expenseDAO.find(Group.CURRENT.getExpenses());
        LocalDate localDate = LocalDate.now();
        localDate.withDayOfMonth(1);
        expenses.removeIf(expense -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(expense.getDate());

            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            int currentMonth = Calendar.getInstance().get(Calendar.MONTH);

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            return year != currentYear || month != currentMonth;
        });
        return expenses;
    }
}

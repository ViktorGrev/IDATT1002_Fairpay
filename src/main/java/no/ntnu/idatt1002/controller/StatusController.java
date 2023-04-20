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
import no.ntnu.idatt1002.data.economy.Income;

import java.net.URL;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.*;

public final class StatusController extends MenuController implements Initializable {

    @FXML private BarChart<String, Long> barChart;
    @FXML private Label statusField;
    @FXML private Text spentSum;
    @FXML private Text incomeSum;
    @FXML private Text budgetSum;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        String monthName = new DateFormatSymbols().getMonths()[month];
        statusField.setText("Status " + monthName + " " + calendar.get(Calendar.YEAR));

        List<Expense> expenses = getMonthlyExpenses();
        List<Income> incomeList = getMonthlyIncome();
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
            long amount = budget.getAmount(expense).longValue();
            budgetSeries.getData().add(new XYChart.Data<>(expense.getCategoryName(), amount));
        }
        barChart.getData().addAll(expenseSeries, budgetSeries);

        spentSum.setText(expenses.stream().mapToLong(value -> value.getAmount().longValue()).sum() + "kr");
        incomeSum.setText(incomeList.stream().mapToLong(value -> value.getAmount().longValue()).sum() + "kr");
        budgetSum.setText(budget.getTotal() + "kr");
    }

    private List<Expense> getMonthlyExpenses() {
        List<Long> expenseIds = Group.CURRENT.getExpenses();
        if(expenseIds.isEmpty()) return new ArrayList<>();
        List<Expense> expenses = expenseDAO.find(expenseIds);
        LocalDate localDate = LocalDate.now();
        localDate.withDayOfMonth(1);
        expenses.removeIf(expense -> isNotThisMonth(expense.getDate()));
        return expenses;
    }

    private List<Income> getMonthlyIncome() {
        List<Long> incomeIds = Group.CURRENT.getIncome();
        if(incomeIds.isEmpty()) return new ArrayList<>();
        List<Income> incomeList = incomeDAO.find(incomeIds);
        LocalDate localDate = LocalDate.now();
        localDate.withDayOfMonth(1);
        incomeList.removeIf(income -> isNotThisMonth(income.getDate()));
        return incomeList;
    }

    private boolean isNotThisMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        return year != currentYear || month != currentMonth;
    }
}

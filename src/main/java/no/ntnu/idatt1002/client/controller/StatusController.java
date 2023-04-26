package no.ntnu.idatt1002.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.model.Group;
import no.ntnu.idatt1002.model.economy.Budget;
import no.ntnu.idatt1002.model.economy.Expense;
import no.ntnu.idatt1002.model.economy.ExpenseType;
import no.ntnu.idatt1002.model.economy.Income;

import java.net.URL;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.*;

/**
 * The controller class for the status page.
 */
public final class StatusController extends MenuController implements Initializable {

  private static final DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(Locale.ENGLISH);

  @FXML private ChoiceBox<String> monthPicker;
  @FXML private ChoiceBox<Integer> yearPicker;
  @FXML private BarChart<String, Long> barChart;
  @FXML private LineChart<String, Long> monthlyChart;
  @FXML private Label statusField;
  @FXML private Text spentSum;
  @FXML private Text incomeSum;
  @FXML private Text budgetSum;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Calendar calendar = Calendar.getInstance();
    String monthName = dateFormatSymbols.getMonths()[calendar.get(Calendar.MONTH)];
    monthPicker.setValue(monthName);
    for(int i = 0; i < 12; i++) {
      String m = dateFormatSymbols.getMonths()[i];
      monthPicker.getItems().add(m);
    }
    monthPicker.setOnAction(e -> showChart());

    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    yearPicker.setValue(currentYear);
    for(int i = currentYear - 3; i <= currentYear; i++) {
      yearPicker.getItems().add(i);
    }
    yearPicker.setOnAction(e -> showChart());

    showChart();
  }

  /**
   * Calculate the monthly and yearly expenses and display
   * the results in charts.
   */
  private void showChart() {
    statusField.setText("Status");

    List<Expense> expenses = getMonthlyExpenses();
    List<Income> incomeList = getMonthlyIncome();
    Map<ExpenseType, Long> expenseMap = new HashMap<>();
    for(Expense expense : expenses) {
      long amount = expense.getAmount().longValue();
      if(!expenseMap.containsKey(expense.getType())) expenseMap.put(expense.getType(), 0L);
      expenseMap.put(expense.getType(), expenseMap.get(expense.getType()) + amount);
    }

    XYChart.Series<String, Long> monthExpenseSeries = new XYChart.Series<>();
    monthExpenseSeries.setName("Expenses");
    List<Expense> yearlyExpenses = getYearlyExpenses();
    for(String month : dateFormatSymbols.getMonths()) {
      long amount = 0;
      for(Expense expense : yearlyExpenses) {
        if(expense.getDate().getMonth() == monthNameToIndex(month))
          amount += expense.getAmount().longValue();
      }
      monthExpenseSeries.getData().add(new XYChart.Data<>(month, amount));
    }
    XYChart.Series<String, Long> monthIncomeSeries = new XYChart.Series<>();
    monthIncomeSeries.setName("Income");
    List<Income> yearlyIncome = getYearlyIncome();
    for(String month : dateFormatSymbols.getMonths()) {
      long amount = 0;
      for(Income income : yearlyIncome) {
        if(income.getDate().getMonth() == monthNameToIndex(month))
          amount += income.getAmount().longValue();
      }
      monthIncomeSeries.getData().add(new XYChart.Data<>(month, amount));
    }
    monthlyChart.getData().clear();
    monthlyChart.getData().addAll(monthExpenseSeries, monthIncomeSeries);

    XYChart.Series<String, Long> expenseSeries = new XYChart.Series<>();
    expenseSeries.setName("Expenses");
    for(ExpenseType expense : ExpenseType.values()) {
      long amount = expenseMap.getOrDefault(expense, 0L);
      expenseSeries.getData().add(new XYChart.Data<>(expense.getName(), amount));
    }

    Budget budget = budgetDAO.find(Group.CURRENT);
    XYChart.Series<String, Long> budgetSeries = new XYChart.Series<>();
    budgetSeries.setName("Budget");
    for(ExpenseType expense : ExpenseType.values()) {
      long amount = budget.getAmount(expense).longValue();
      budgetSeries.getData().add(new XYChart.Data<>(expense.getName(), amount));
    }
    barChart.getData().clear();
    barChart.getData().addAll(expenseSeries, budgetSeries);

    spentSum.setText(expenses.stream().mapToLong(value -> value.getAmount().longValue()).sum() + " kr");
    incomeSum.setText(incomeList.stream().mapToLong(value -> value.getAmount().longValue()).sum() + " kr");
    budgetSum.setText(budget.getTotal() + " kr");
  }

  private List<Expense> getMonthlyExpenses() {
    Group group = getGroup(Group.CURRENT);
    List<Long> expenseIds = group.getExpenses();
    if(expenseIds.isEmpty()) return new ArrayList<>();
    List<Expense> expenses = expenseDAO.find(expenseIds);
    expenses.removeIf(expense -> isNotThisMonth(expense.getDate()));
    return expenses;
  }

  private List<Expense> getYearlyExpenses() {
    Group group = getGroup(Group.CURRENT);
    List<Long> expenseIds = group.getExpenses();
    if(expenseIds.isEmpty()) return new ArrayList<>();
    List<Expense> expenses = expenseDAO.find(expenseIds);
    expenses.removeIf(expense -> isNotThisYear(expense.getDate()));
    return expenses;
  }

  private List<Income> getYearlyIncome() {
    Group group = getGroup(Group.CURRENT);
    List<Long> incomeIds = group.getIncome();
    if(incomeIds.isEmpty()) return new ArrayList<>();
    List<Income> incomeList = incomeDAO.find(incomeIds);
    incomeList.removeIf(income -> isNotThisYear(income.getDate()));
    return incomeList;
  }

  private List<Income> getMonthlyIncome() {
    Group group = getGroup(Group.CURRENT);
    List<Long> incomeIds = group.getIncome();
    if(incomeIds.isEmpty()) return new ArrayList<>();
    List<Income> incomeList = incomeDAO.find(incomeIds);
    incomeList.removeIf(income -> isNotThisMonth(income.getDate()));
    return incomeList;
  }

  private boolean isNotThisMonth(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);

    int currentYear = getPickedYear();
    int currentMonth = getPickedMonth();

    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    return year != currentYear || month != currentMonth;
  }

  private boolean isNotThisYear(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);

    int currentYear = getPickedYear();

    int year = calendar.get(Calendar.YEAR);
    return year != currentYear;
  }

  private int getPickedMonth() {
    return monthNameToIndex(monthPicker.getValue());
  }

  private int monthNameToIndex(String monthName) {
    for(int i = 0; i < 12; i++) {
      if(dateFormatSymbols.getMonths()[i].equals(monthName))
        return i;
    }
    return -1;
  }

  private int getPickedYear() {
    return yearPicker.getValue();
  }
}

package no.ntnu.idatt1002.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.model.Group;
import no.ntnu.idatt1002.model.economy.Budget;
import no.ntnu.idatt1002.model.economy.ExpenseType;
import no.ntnu.idatt1002.client.view.Page;
import no.ntnu.idatt1002.util.TableEditor;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the budget page.
 */
public final class BudgetController extends MenuController implements Initializable {

  @FXML private TableView<ExpenseType> budgetTable;
  @FXML private BarChart<String, Long> barChart;
  @FXML private Text sum;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Budget budget = budgetDAO.find(Group.CURRENT);

    TableEditor<ExpenseType> budgetTableEditor = new TableEditor<>(budgetTable)
            .addColumn("Category", ExpenseType::getName)
            .addColumn("Amount (kr)", type -> createInputField(type, budget.getAmount(type).longValue()));
    for(ExpenseType type : ExpenseType.values())
      budgetTableEditor.addRow(type);

    sum.setText(budget.getTotal() + "kr");

    XYChart.Series<String, Long> series = new XYChart.Series<>();
    series.setName("Budget");
    for(ExpenseType expense : ExpenseType.values()) {
      long amount = budget.getAmount(expense).longValue();
      series.getData().add(new XYChart.Data<>(expense.getName(), amount));
    }
    barChart.getData().add(series);
  }

  /**
   * Creates the input field for each expense type.
   *
   * @param   type the expense type
   * @param   amount the amount
   * @return  a TextField
   */
  private TextField createInputField(ExpenseType type, long amount) {
    TextField inputField = new TextField();
    if(amount != 0) inputField.setText(String.valueOf(amount));
    inputField.setPromptText("0");
    inputField.setOnKeyPressed(event -> {
      if(event.getCode() == KeyCode.ENTER) {
        if(inputField.getText().isBlank()) return;
        budgetDAO.addType(Group.CURRENT, type, BigDecimal.valueOf(Long.parseLong(inputField.getText())));
        viewPage(Page.BUDGET);
      }
    });

    // Ensure only numbers
    inputField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("\\d*")) {
        inputField.setText(newValue.replaceAll("[^\\d]", ""));
      }
    });
    inputField.setOnMouseClicked(e -> {
      String text = inputField.getText();
      if(text.equals("0")) inputField.setText("");
    });
    return inputField;
  }
}

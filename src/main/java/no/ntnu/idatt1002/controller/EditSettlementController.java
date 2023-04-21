package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.User;
import no.ntnu.idatt1002.data.economy.Expense;
import no.ntnu.idatt1002.data.economy.Settlement;
import no.ntnu.idatt1002.scene.SceneSwitcher;
import no.ntnu.idatt1002.util.DateUtil;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public final class EditSettlementController extends MenuController implements Initializable {

  @FXML private TableView<Expense> expenseTable;
  @FXML private Label nameField;
  @FXML private VBox memberBox;

  @FXML
  private void addExpenseClick() {
    SceneSwitcher.setView("addSettlementExpense");
  }

  @FXML
  private void deleteSettlementClick() {
    Settlement settlement = settlementDAO.find(SettlementController.settlementId);
    settlementDAO.delete(settlement.getId());
    SceneSwitcher.setView("settlement");
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Settlement settlement = settlementDAO.find(SettlementController.settlementId);
    nameField.setText(settlement.getName());

    Group group = groupDAO.find(Group.CURRENT);

    for(User user : group.getMembers()) {
      RadioButton radioButton = new RadioButton();
      if(settlement.getMembers().contains(user.getId())) {
        radioButton.setSelected(true);
        if(user.getId() == User.CURRENT) {
          radioButton.setDisable(true);
        }
      }
      radioButton.setOnMouseClicked(e -> {
        if(user.getId() == User.CURRENT) return;
        if(radioButton.isSelected()) {
          settlementDAO.addMember(settlement.getId(), user.getId());
          settlement.addMember(user.getId());
        } else {
          settlementDAO.removeMember(settlement.getId(), user.getId());
          settlement.removeMember(user.getId());
        }
      });
      Label label = new Label(user.getUsername());
      label.setStyle("-fx-border-style: hidden hidden solid hidden; -fx-min-width: 98px; -fx-border-radius: 0em 5px; -fx-border-color:  #793f3f; -fx-text-fill:  #793f3f");
      HBox hBox = new HBox(radioButton, label);
      memberBox.getChildren().add(hBox);
    }

    TableEditor<Expense> expenseTableEditor = new TableEditor<>(expenseTable)
            .setPlaceholder("No expenses")
            .addColumn("Name", expense -> expense.hasName() ? expense.getName() : expense.getType().getCategoryName())
            .addColumn("Price", Expense::getAmount)
            .addColumn("Date", expense -> DateUtil.format(expense.getDate().getTime(), "dd MMM yyyy"));

    if(!settlement.getExpenses().isEmpty()) {
      List<Expense> expenses = expenseDAO.find(settlement.getExpenses());
      expenseTableEditor.addRows(expenses);
    }
  }
}

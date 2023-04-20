package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.User;
import no.ntnu.idatt1002.data.economy.Expense;
import no.ntnu.idatt1002.data.economy.Income;
import no.ntnu.idatt1002.scene.SceneSwitcher;
import no.ntnu.idatt1002.util.DateUtil;
import no.ntnu.idatt1002.util.UserUtil;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public final class IncomeController extends MenuController implements Initializable {

  @FXML private TableView<Income> incomeTable;
  @FXML private Text totalAmountField;
  @FXML private VBox mustPayField;

  @FXML
  private void newIncomeClick() {
    SceneSwitcher.setView("newIncome");
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    User user = User.CURRENT;
    Group group = Group.CURRENT;
    Map<Long, User> users = UserUtil.toMap(group.getMembers());

    TableEditor<Income> incomeTableTableEditor = new TableEditor<>(incomeTable)
            .setPlaceholder("No income")
            .addColumn("Name", Income::getName)
            .addColumn("Added by", income -> users.get(income.getUserId()).getUsername())
            .addColumn("Amount (kr)", Income::getAmount)
            .addColumn("Your share (kr)", income -> income.getAmount().longValue() / income.getShares())
            .addColumn("Date", income -> DateUtil.format(income.getDate().getTime(), "dd MMM yyyy"))
            .addColumn("Received", income -> income.getUserId() == user.getId() ? null : createReceivedBox(income));

    List<Long> incomeIds = group.getIncome();
    if(!incomeIds.isEmpty()) {
      List<Income> incomeList = incomeDAO.find(incomeIds);
      if(!incomeList.isEmpty()) {
        incomeTableTableEditor.addRows(incomeList);
      }
    }

    calculateTotal();
  }

  private void calculateTotal() {
    User user = User.CURRENT;
    Group group = Group.CURRENT;
    int total = 0;
    Map<Long, Long> owes = new HashMap<>();

    List<Long> incomeIds = group.getIncome();
    if(!incomeIds.isEmpty()) {
      List<Income> incomes = incomeDAO.find(incomeIds);
      for(Income income : incomes) {
        long incomeUserId = income.getUserId();
        if(incomeUserId == user.getId()) continue;
        if(!group.isIncomeReceived(income.getIncomeId(), user.getId())) {
          long amount = income.getAmount().longValue() / group.getMembers().size();
          total += amount;
          if(!owes.containsKey(incomeUserId)) owes.put(incomeUserId, 0L);
          owes.put(incomeUserId, owes.get(incomeUserId) + amount);
        }
      }
    }
    totalAmountField.setText(total + "kr");

    Map<Long, User> users = UserUtil.toMap(group.getMembers());
    mustPayField.getChildren().removeIf(node -> !(node instanceof Label));
    for(long l : owes.keySet()) {
      HBox hBox = new HBox();
      hBox.getChildren().addAll(new Label(users.get(l).getUsername() + ": "), new Label(owes.get(l) + "kr"));
      mustPayField.setStyle("-fx-font-size: 14");
      mustPayField.getChildren().add(hBox);
    }
  }

  private CheckBox createReceivedBox(Income income) {
    long userId = User.CURRENT.getId();
    long incomeId = income.getIncomeId();

    CheckBox checkBox = new CheckBox();
    if(Group.CURRENT.isIncomeReceived(incomeId, userId)) {
      checkBox.setSelected(true);
    }

    checkBox.setOnAction(e -> {
      if(checkBox.isSelected()) {
        groupDAO.setReceivedIncome(incomeId, userId);
        Group.CURRENT.addReceivedIncome(incomeId, userId);
      } else {
        groupDAO.unsetReceivedIncome(incomeId, userId);
        Group.CURRENT.removeReceivedIncome(incomeId, userId);
      }
      calculateTotal();
    });
    return checkBox;
  }
}

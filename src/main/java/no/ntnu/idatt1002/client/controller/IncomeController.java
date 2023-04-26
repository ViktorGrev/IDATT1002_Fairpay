package no.ntnu.idatt1002.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.model.Group;
import no.ntnu.idatt1002.model.User;
import no.ntnu.idatt1002.model.economy.Income;
import no.ntnu.idatt1002.client.view.Page;
import no.ntnu.idatt1002.util.DateUtil;
import no.ntnu.idatt1002.util.TableEditor;
import no.ntnu.idatt1002.util.UserUtil;

import java.net.URL;
import java.util.*;

/**
 * The controller class for the income page.
 */
public final class IncomeController extends MenuController implements Initializable {

  @FXML private TableView<Income> incomeTable;
  @FXML private Text totalAmountField;
  @FXML private Text totalAmountReceive;

  /**
   * Send the user to the add income page.
   */
  @FXML
  private void newIncomeClick() {
    viewPage(Page.INCOME_ADD);
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    Group group = getGroup(Group.CURRENT);
    Map<Long, User> users = UserUtil.toMap(group.getMembers());

    TableEditor<Income> incomeTableTableEditor = new TableEditor<>(incomeTable)
            .setPlaceholder("No income")
            .addColumn("Name", Income::getName)
            .addColumn("Added by", income -> users.get(income.getUserId()).getUsername())
            .addColumn("Amount (kr)", Income::getAmount)
            .addColumn("Your share (kr)", income -> income.getAmount().longValue() / income.getShares())
            .addColumn("Date", income -> DateUtil.format(income.getDate().getTime(), "dd MMM yyyy"))
            .addColumn("Status", this::createStatus)
            .addColumn(this::createDeleteButton);

    List<Long> incomeIds = group.getIncome();
    if(!incomeIds.isEmpty()) {
      List<Income> incomeList = incomeDAO.find(incomeIds);
      if(!incomeList.isEmpty()) {
        incomeList.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
        incomeTableTableEditor.addRows(incomeList);
      }
    }

    calculateTotal();
  }

  /**
   * Calculates the amount to pay and receive for the user.
   */
  private void calculateTotal() {
    Group group = getGroup(Group.CURRENT);
    int totalPay = 0;
    int totalReceive = 0;
    Map<Long, Long> owes = new HashMap<>();

    List<Long> incomeIds = group.getIncome();
    if(!incomeIds.isEmpty()) {
      List<Income> incomes = incomeDAO.find(incomeIds);
      for(Income income : incomes) {
        for(User groupUser : group.getMembers()) {
          long incomeUserId = income.getUserId();
          long userId = groupUser.getId();
          if(incomeUserId != User.CURRENT) continue;
          if(userId == incomeUserId) continue;
          if(!group.isIncomeReceived(income.getId(), groupUser.getId())) {
            long amount = income.getAmount().longValue() / income.getShares();
            totalPay += amount;
            if(!owes.containsKey(userId)) owes.put(userId, 0L);
            owes.put(userId, owes.get(userId) + amount);
          }
        }
      }
      for(Income income : incomes) {
        if(User.CURRENT != income.getUserId()) {
          if(!group.isIncomeReceived(income.getId(), User.CURRENT)) {
            long amount = income.getAmount().longValue() / income.getShares();
            totalReceive += amount;
          }
        }
      }
    }
    totalAmountField.setText(totalPay + " kr");
    totalAmountReceive.setText(totalReceive + " kr");
  }

  /**
   * Creates a checkbox if the income is not the users income.
   * If it is, it will return a display of how many have paid their
   * share, or a check mark if everyone paid their share.
   * @param   income the income
   * @return  the status object
   */
  private Object createStatus(Income income) {
    Group group = getGroup(Group.CURRENT);
    long userId = User.CURRENT;
    long incomeId = income.getId();

    if(userId == income.getUserId()) {
      long receivedCount = group.getReceivedIncome().getOrDefault(incomeId, new ArrayList<>()).size();
      int total = income.getShares() - 1;
      if(receivedCount == total) {
        Image image = new Image(ExpenseController.class.getResourceAsStream("/image/check.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        return imageView;
      }
      return new Label(receivedCount + "/" + total + " paid");
    }

    CheckBox checkBox = new CheckBox("Received");
    checkBox.setTooltip(new Tooltip("Mark as received"));
    if(group.isIncomeReceived(incomeId, userId)) {
      checkBox.setSelected(true);
    }

    checkBox.setOnAction(e -> {
      if(checkBox.isSelected()) {
        groupDAO.setReceivedIncome(incomeId, userId);
        group.addReceivedIncome(incomeId, userId);
      } else {
        groupDAO.unsetReceivedIncome(incomeId, userId);
        group.removeReceivedIncome(incomeId, userId);
      }
      calculateTotal();
    });
    return checkBox;
  }

  /**
   * Creates the delete button.
   *
   * @param   income the income to delete
   * @return  a button
   */
  private Button createDeleteButton(Income income) {
    if(income.getUserId() != User.CURRENT) return null;
    Button button = new Button();
    Image image = new Image(ExpenseController.class.getResourceAsStream("/image/delete_dark.png"));
    ImageView imageView = new ImageView(image);
    imageView.setFitHeight(21);
    imageView.setFitWidth(21);
    button.getStyleClass().add("deleteButton");
    button.setGraphic(imageView);
    button.setTooltip(new Tooltip("Delete income"));
    button.setOnMouseClicked(event -> {
      groupDAO.removeIncome(Group.CURRENT, income.getId());
      incomeDAO.delete(income.getId());
      incomeTable.getItems().removeIf(i -> i.getId() == income.getId());
      calculateTotal();
    });
    return button;
  }
}

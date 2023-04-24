package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.FairPay;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.User;
import no.ntnu.idatt1002.data.economy.Expense;
import no.ntnu.idatt1002.data.economy.Income;
import no.ntnu.idatt1002.scene.SceneSwitcher;
import no.ntnu.idatt1002.util.DateUtil;
import no.ntnu.idatt1002.util.UserUtil;

import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ExpenseController extends MenuController implements Initializable {

    @FXML private TableView<Expense> expenseTable;
    @FXML private Text totalAmountField;
    @FXML private Text totalAmountReceive;

    @FXML
    private void addExpenseClick() {
        SceneSwitcher.setView("addExpense");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Group group = getGroup(Group.CURRENT);
        Map<Long, User> users = UserUtil.toMap(group.getMembers());

        TableEditor<Expense> expenseTableEditor = new TableEditor<>(expenseTable)
                .setPlaceholder("No expenses")
                .addColumn("Name", expense -> expense.hasName() ? expense.getName() : expense.getType().getCategoryName())
                .addColumn("Added by", expense -> users.get(expense.getUserId()).getUsername())
                .addColumn("Amount (kr)", Expense::getAmount)
                .addColumn("Your share (kr)", expense -> expense.getAmount().longValue() / expense.getShares())
                .addColumn("Date", expense -> DateUtil.format(expense.getDate().getTime(), "dd MMM yyyy"))
                .addColumn("Status", this::createPaidBox)
                .addColumn(this::createDeleteButton);

        List<Long> expenseIds = group.getExpenses();
        if(!expenseIds.isEmpty()) {
            List<Expense> expenses = expenseDAO.find(expenseIds);
            if(!expenses.isEmpty()) {
                expenses.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
                expenseTableEditor.addRows(expenses);
            }
        }

        calculateTotal();
    }

    private void calculateTotal() {
        Group group = getGroup(Group.CURRENT);
        int total = 0;
        int totalReceive = 0;
        Map<Long, Long> owes = new HashMap<>();

        List<Long> expenseIds = group.getExpenses();
        if(!expenseIds.isEmpty()) {
            List<Expense> expenses = expenseDAO.find(expenseIds);
            for(Expense expense : expenses) {
                long expenseUserId = expense.getUserId();
                if(expenseUserId == User.CURRENT) continue;
                if(!group.isPaid(expense.getExpenseId(), User.CURRENT)) {
                    long amount = expense.getAmount().longValue() / group.getMembers().size();
                    total += amount;
                    if(!owes.containsKey(expenseUserId)) owes.put(expenseUserId, 0L);
                    owes.put(expenseUserId, owes.get(expenseUserId) + amount);
                }
            }
            for(Expense expense : expenses) {
                if(User.CURRENT == expense.getUserId()) {
                    long paidCount = group.getPaidExpenses().getOrDefault(expense.getExpenseId(), new ArrayList<>()).size();
                    int totalPaid = expense.getShares() - 1;
                    if(paidCount == totalPaid) continue;
                    long amount = expense.getAmount().longValue() / expense.getShares();
                    totalReceive += amount;
                }
            }
        }
        totalAmountField.setText(total + " kr");

        totalAmountReceive.setText(totalReceive + " kr");
    }

    private Object createPaidBox(Expense expense) {
        Group group = getGroup(Group.CURRENT);
        long userId = User.CURRENT;
        long expenseId = expense.getExpenseId();

        if(userId == expense.getUserId()) {
            long receivedCount = group.getPaidExpenses().getOrDefault(expenseId, new ArrayList<>()).size();
            int total = expense.getShares() - 1;
            if(receivedCount == total) {
                Image image = new Image(ExpenseController.class.getResourceAsStream("/image/check.png"));
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(20);
                imageView.setFitWidth(20);
                return imageView;
            }
            return new Label(receivedCount + "/" + total + " received");
        }

        CheckBox checkBox = new CheckBox("Paid");
        checkBox.setTooltip(new Tooltip("Mark as paid"));
        if(group.isPaid(expenseId, userId)) {
            checkBox.setSelected(true);
        }

        checkBox.setOnAction(e -> {
            if(checkBox.isSelected()) {
                groupDAO.setPaidExpense(expenseId, userId);
                group.addPaidExpense(expenseId, userId);
            } else {
                groupDAO.unsetPaidExpense(expenseId, userId);
                group.removePaidExpense(expenseId, userId);
            }
            calculateTotal();
        });
        return checkBox;
    }

    private Button createDeleteButton(Expense expense) {
        if(expense.getUserId() != User.CURRENT) return null;
        Button button = new Button();
        Image image = new Image(ExpenseController.class.getResourceAsStream("/image/delete_dark.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        button.getStyleClass().add("deleteButton");
        button.setGraphic(imageView);
        button.setTooltip(new Tooltip("Delete expense"));
        button.setOnMouseClicked(event -> {
            groupDAO.removeExpense(Group.CURRENT, expense.getExpenseId());
            expenseDAO.delete(expense.getExpenseId());
            expenseTable.getItems().removeIf(e -> e.getExpenseId() == expense.getExpenseId());
            calculateTotal();
        });
        return button;
    }
}

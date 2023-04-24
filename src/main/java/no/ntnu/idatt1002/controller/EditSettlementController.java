package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.User;
import no.ntnu.idatt1002.data.economy.Expense;
import no.ntnu.idatt1002.data.economy.ExpenseType;
import no.ntnu.idatt1002.data.economy.Settlement;
import no.ntnu.idatt1002.scene.SceneSwitcher;
import no.ntnu.idatt1002.util.DateUtil;
import no.ntnu.idatt1002.util.UserUtil;

import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

public final class EditSettlementController extends MenuController implements Initializable {

    @FXML private Pane paymentsPane;
    @FXML private TableView<Expense> expenseTable;
    @FXML private Label nameField;
    @FXML private VBox memberBox;
    @FXML private Button deleteButton;
    @FXML private Button endButton;

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

    @FXML
    private void endSettlementClick() {
        Settlement settlement = settlementDAO.find(SettlementController.settlementId);
        settlementDAO.setEnded(settlement.getId(), true);
        settlement.setEnded(true);
        endButton.setVisible(false);
        showPayments(settlement);
    }

    @FXML
    private void goBackClick() {
        SceneSwitcher.setView("settlement");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Settlement settlement = settlementDAO.find(SettlementController.settlementId);
        nameField.setText(settlement.getName());

        if(settlement.getUserId() != User.CURRENT) {
            deleteButton.setVisible(false);
            endButton.setVisible(false);
            memberBox.setDisable(true);
        } else {
            if(settlement.isEnded()) {
                endButton.setVisible(false);
            }
        }

        if(settlement.isEnded()) {
            showPayments(settlement);
        }

        Group group = groupDAO.find(Group.CURRENT);

        for(User user : group.getMembers()) {
            RadioButton radioButton = new RadioButton();
            if(settlement.getMembers().contains(user.getId())) {
                radioButton.setSelected(true);
                radioButton.setDisable(user.getId() == User.CURRENT);
            }
            radioButton.setStyle("-fx-cursor: hand; -fx-padding: 0px 7px 0px 0px;");
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
            label.setStyle("-fx-font-size: 14px;");
            HBox hBox = new HBox(radioButton, label);
            hBox.setStyle("-fx-padding: 5px");
            memberBox.getChildren().add(hBox);
        }

        Map<Long, User> users = UserUtil.toMap(group.getMembers());

        TableEditor<Expense> expenseTableEditor = new TableEditor<>(expenseTable)
                .setPlaceholder("No expenses")
                .addColumn("Name", expense -> expense.hasName() ? expense.getName() : expense.getType().getCategoryName())
                .addColumn("Added by", expense -> users.get(expense.getUserId()).getUsername())
                .addColumn("Amount (kr)", Expense::getAmount)
                .addColumn("Date", expense -> DateUtil.format(expense.getDate().getTime(), "dd MMM yyyy"));

        if(!settlement.getExpenses().isEmpty()) {
            List<Expense> expenses = expenseDAO.find(settlement.getExpenses());
            expenseTableEditor.addRows(expenses);
        }
    }

    private void showPayments(Settlement settlement) {
        expenseTable.setVisible(false);
        memberBox.setDisable(true);
        if(settlement.getExpenses().isEmpty()) return;
        List<Expense> expenses = expenseDAO.find(settlement.getExpenses());

        long total = expenses.stream().mapToLong(e -> e.getAmount().longValue()).sum();
        long each = total / settlement.getMembers().size();
        //System.out.println("total: " + total);
        //System.out.println("each must pay " + each);

        Map<Long, Long> receiveMap = new HashMap<>();
        Map<Long, Long> payMap = new HashMap<>();

        getUsers(expenses.stream().map(Expense::getUserId).toList());

        VBox vBox = new VBox();
        HBox hBox = new HBox();
        Label totalText = new Label("Total amount: ");
        totalText.setStyle("-fx-padding: 10px; -fx-font-size: 16px;");
        Label totalAmount = new Label(total + " kr");
        totalAmount.setStyle("-fx-padding: 10px; -fx-font-size: 18px;");
        totalAmount.setTextFill(Paint.valueOf("#572828"));
        hBox.getChildren().addAll(totalText, totalAmount);
        vBox.getChildren().add(hBox);
        for(long userId : settlement.getMembers()) {
            //System.out.println("userId " + userId);
            long diff = getUserAmount(userId, expenses) - each;
            Label label = null;
            if(diff < 0) {
                //System.out.println("user " + userId + " must pay " + Math.abs(diff));
                payMap.put(userId, Math.abs(diff));
                label = new Label(getUser(userId).getUsername() + " must pay " + Math.abs(diff));
            } else if(diff > 0) {
                //System.out.println("user " + userId + " must receive " + diff);
                receiveMap.put(userId, diff);
                label = new Label(getUser(userId).getUsername() + " must receive " + diff);
            }
            if(label == null) continue;
            label.setStyle("-fx-padding: 10px; -fx-font-size: 16px;");
            vBox.getChildren().add(label);
        }
        paymentsPane.getChildren().add(vBox);
    }

    public static void main(String[] args) {
        List<Long> members = Arrays.asList(1L, 2L, 3L);
        List<Expense> expenses = Arrays.asList(
                new Expense(1, 1, new Date(), ExpenseType.CAR,
                        "Name", BigDecimal.valueOf(10), new Date(), 1),
                new Expense(2, 2, new Date(), ExpenseType.CAR,
                        "Name", BigDecimal.valueOf(20), new Date(), 1),
                new Expense(3, 3, new Date(), ExpenseType.CAR,
                        "Name", BigDecimal.valueOf(90), new Date(), 1)
        );
        long total = expenses.stream().mapToLong(e -> e.getAmount().longValue()).sum();
        long each = total / members.size();
        System.out.println("total: " + total);
        System.out.println("each must pay " + each);

        Map<Long, Long> receiveMap = new HashMap<>();
        Map<Long, Long> payMap = new HashMap<>();

        for(long userId : members) {
            long diff = getUserAmount(userId, expenses) - each;
            if(diff < 0) {
                System.out.println("user " + userId + " must pay " + Math.abs(diff));
                payMap.put(userId, Math.abs(diff));
                //paymentsPane.getChildren().add(new Label());
            } else if(diff > 0) {
                System.out.println("user " + userId + " must receive " + diff);
                receiveMap.put(userId, diff);
            }
        }

        Map<Long, Map<Long, Long>> resultMap = new HashMap<>();

        for(long payUserId : payMap.keySet()) {
            long toPay = payMap.get(payUserId);
            System.out.println("toPay " + payUserId + " " + toPay);
            Iterator<Long> receiveIter = receiveMap.keySet().iterator();
            while(receiveIter.hasNext()) {
                long receiveUserId = receiveIter.next();
                long toReceive = receiveMap.get(receiveUserId);
                if(toPay >= toReceive) {
                    toPay -= toReceive;
                    receiveIter.remove();
                    //Map<Long, Long> m = resultMap.computeIfAbsent(payUserId, k -> new HashMap<>());
                    //m.put(toReceiveId, m.getOrDefault(toReceiveId, 0L) + toReceive);
                }
                System.out.println("");
            }
        }

        /*for(long payUserId : payMap.keySet()) {
            long toPay = payMap.get(payUserId);
            System.out.println("toPay " + payUserId + " " + toPay);
            Iterator<Map.Entry<Long, Long>> receiveIter = receiveMap.entrySet().iterator();
            while(receiveIter.hasNext()) {
                Map.Entry<Long, Long> entry = receiveIter.next();
                long toReceiveId = entry.getKey();
                long toReceive = entry.getValue();
                if(toPay >= toReceive) {
                    toPay -= toReceive;
                    receiveIter.remove();
                    System.out.println("add pay " + toReceiveId + " " + toReceive + " " + toPay);
                    Map<Long, Long> m = resultMap.computeIfAbsent(payUserId, k -> new HashMap<>());
                    m.put(toReceiveId, m.getOrDefault(toReceiveId, 0L) + toReceive);
                } else {
                    System.out.println("add pay " + toReceiveId + " " + toReceive + " " + toPay);
                    Map<Long, Long> m = resultMap.computeIfAbsent(payUserId, k -> new HashMap<>());
                    m.put(toReceiveId, m.getOrDefault(toReceiveId, 0L) + toReceive);
                }
            }
        }*/

        for(long userId : resultMap.keySet()) {
            for(long targetId : resultMap.get(userId).keySet()) {
                System.out.println(userId + " must pay " + targetId + " " + resultMap.get(userId).get(targetId));
            }
        }

    }

    private static long getUserAmount(long userId, List<Expense> expenses) {
        long total = 0;
        for(Expense expense : expenses) {
            if(expense.getUserId() != userId) continue;
            total += expense.getAmount().longValue();
        }
        return total;
    }
}

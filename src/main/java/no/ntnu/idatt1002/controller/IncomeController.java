package no.ntnu.idatt1002.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import java.util.*;

public final class IncomeController extends MenuController implements Initializable {

    @FXML private TableView<Income> incomeTable;
    @FXML private Text totalAmountField;
    @FXML private Text totalAmountReceive;
    //@FXML private VBox mustPayField;

    @FXML
    private void newIncomeClick() {
        SceneSwitcher.setView("newIncome");
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
                .addColumn("Status", this::createReceivedBox)
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
                    if(!group.isIncomeReceived(income.getIncomeId(), groupUser.getId())) {
                        long amount = income.getAmount().longValue() / income.getShares();
                        totalPay += amount;
                        if(!owes.containsKey(userId)) owes.put(userId, 0L);
                        owes.put(userId, owes.get(userId) + amount);
                    }
                }
            }
            for(Income income : incomes) {
                if(User.CURRENT != income.getUserId()) {
                    if(!group.isIncomeReceived(income.getIncomeId(), User.CURRENT)) {
                        long amount = income.getAmount().longValue() / income.getShares();
                        totalReceive += amount;
                    }
                }
            }
        }
        totalAmountField.setText(totalPay + " kr");

        totalAmountReceive.setText(totalReceive + " kr");

        /*mustPayField.getChildren().clear();
        if(totalPay != 0) {
            Map<Long, User> users = UserUtil.toMap(group.getMembers());
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setFitToWidth(true);
            VBox vBox = new VBox();
            for(long l : owes.keySet()) {
                HBox hBox = new HBox();
                hBox.getChildren().addAll(new Label(users.get(l).getUsername() + ": "), new Label(owes.get(l) + "kr"));
                hBox.setStyle("-fx-font-size: 14");
                vBox.getChildren().add(hBox);
            }
            scrollPane.setContent(vBox);
            mustPayField.getChildren().add(new Label("You must pay the following:"));
            mustPayField.getChildren().add(scrollPane);
        }*/
    }

    private Object createReceivedBox(Income income) {
        Group group = getGroup(Group.CURRENT);
        long userId = User.CURRENT;
        long incomeId = income.getIncomeId();

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
            groupDAO.removeIncome(Group.CURRENT, income.getIncomeId());
            incomeDAO.delete(income.getIncomeId());
            incomeTable.getItems().removeIf(i -> i.getIncomeId() == income.getIncomeId());
            calculateTotal();
        });
        return button;
    }
}

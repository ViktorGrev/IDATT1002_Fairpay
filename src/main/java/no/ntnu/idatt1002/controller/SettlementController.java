package no.ntnu.idatt1002.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import no.ntnu.idatt1002.data.User;
import no.ntnu.idatt1002.data.economy.Settlement;
import no.ntnu.idatt1002.scene.SceneSwitcher;
import no.ntnu.idatt1002.util.DateUtil;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public final class SettlementController extends MenuController implements Initializable {

    static long settlementId;

    @FXML private TableView<Settlement> settlementTable;

    @FXML
    private void newSettlementClick() {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setHeaderText("Settlement name");
        inputDialog.setTitle("Settlement name");

        Optional<String> input = inputDialog.showAndWait();
        input.ifPresent(s -> {
            if(s.isBlank()) return;
            Settlement settlement = settlementDAO.create(s, User.CURRENT);
            settlement.addMember(User.CURRENT);
            settlementDAO.addMember(settlement.getId(), User.CURRENT);
            settlementId = settlement.getId();
            SceneSwitcher.setView("editSettlement");
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        settlementTable.setStyle("-fx-cursor: hand");
        settlementTable.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
                Settlement settlement = settlementTable.getSelectionModel().getSelectedItem();
                if(settlement == null) return;
                settlementId = settlement.getId();
                SceneSwitcher.setView("editSettlement");
            }
        });

        List<Settlement> settlements = settlementDAO.findByUser(User.CURRENT);
        new TableEditor<>(settlementTable)
                .setPlaceholder("No settlements")
                .addColumn("Name", Settlement::getName)
                .addColumn("Status", this::createStatusLabel)
                .addColumn("Members", settlement -> settlement.getMembers().size())
                .addColumn("Expenses", settlement -> settlement.getExpenses().size())
                .addColumn("Date", settlement -> DateUtil.format(settlement.getDate().getTime(), "dd MMM yyyy"))
                .addRows(settlements);
    }

    private Label createStatusLabel(Settlement settlement) {
        Label label = new Label(settlement.isEnded() ? "Completed" : "Ongoing");
        if(settlement.isEnded()) {
            label.setStyle("-fx-background-color: #a8ff82; -fx-border-color: #49f000; -fx-border-radius: 5px; -fx-padding: 2px;");
        } else {
            label.setStyle("-fx-background-color: #f5f57d; -fx-border-color: #fdeb0a; -fx-border-radius: 5px; -fx-padding: 2px;");
        }
        return label;
    }
}

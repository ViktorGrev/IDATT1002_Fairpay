package no.ntnu.idatt1002.controller;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import no.ntnu.idatt1002.SceneSwitcher;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StatusController implements Initializable {

    @FXML
    public PieChart pieChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Malin", 200),
                new PieChart.Data("Viktor", 100),
                new PieChart.Data("Sommer", 100),
                new PieChart.Data("Ina", 220),
                new PieChart.Data("Henrik", 230));

        pieChartData.forEach(data ->
            data.nameProperty().bind(
                    Bindings.concat(
                            data.getName(), " amount ", data.pieValueProperty()
                    )
            )
        );

        pieChart.getData().addAll(pieChartData);
    }
}
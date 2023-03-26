package no.ntnu.idatt1002.controller;

import javafx.event.ActionEvent;
import no.ntnu.idatt1002.Scenes.SceneSwitcher;

import java.io.IOException;

public class ProfileController {
    public void homeClick(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.setView("homePage");
    }
    public void expenseClick(ActionEvent actionEvent) throws IOException {
        SceneSwitcher.setView("expense");
    }

    public void statusClick(ActionEvent actionEvent) throws IOException{
        SceneSwitcher.setView("status");
    }

    public void settlementClick(ActionEvent actionEvent) throws IOException{
        SceneSwitcher.setView("settlement");
    }

    public void incomeClick(ActionEvent actionEvent) throws IOException{
        SceneSwitcher.setView("income");
    }

    public void budgetClick(ActionEvent actionEvent) {
        SceneSwitcher.setView("budget");
    }

    public void dormGroupClick(ActionEvent actionEvent) {
        SceneSwitcher.setView("dormGroup");
    }
}

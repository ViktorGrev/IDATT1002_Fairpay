module no.ntnu.idatt1002 {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires java.sql;

    opens no.ntnu.idatt1002.model;
    opens no.ntnu.idatt1002.client.controller to javafx.fxml;
    opens no.ntnu.idatt1002.model.economy;
    opens no.ntnu.idatt1002.util;
    opens no.ntnu.idatt1002.client.view;
    opens no.ntnu.idatt1002.dao.sqlite;
    opens no.ntnu.idatt1002 to javafx.fxml;


    exports no.ntnu.idatt1002;
    exports no.ntnu.idatt1002.dao;
    exports no.ntnu.idatt1002.dao.sqlite;
    exports no.ntnu.idatt1002.dao.exception;
    exports no.ntnu.idatt1002.model;
    exports no.ntnu.idatt1002.model.economy;
    exports no.ntnu.idatt1002.client.controller;
    exports no.ntnu.idatt1002.client.view;
    exports no.ntnu.idatt1002.util;
}
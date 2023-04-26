module no.ntnu.idatt1002 {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires java.sql;

    opens no.ntnu.idatt1002.data;
    opens no.ntnu.idatt1002.controller to javafx.fxml;
    opens no.ntnu.idatt1002.data.economy;
    opens no.ntnu.idatt1002.util;
    opens no.ntnu.idatt1002.scene;
    opens no.ntnu.idatt1002.dao.sqlite;
    opens no.ntnu.idatt1002 to javafx.fxml;


    exports no.ntnu.idatt1002;
    exports no.ntnu.idatt1002.dao;
    exports no.ntnu.idatt1002.dao.sqlite;
    exports no.ntnu.idatt1002.dao.exception;
    exports no.ntnu.idatt1002.data;
    exports no.ntnu.idatt1002.data.economy;
    exports no.ntnu.idatt1002.controller;
    exports no.ntnu.idatt1002.scene;
    exports no.ntnu.idatt1002.util;
}
module no.ntnu.idatt1002 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires com.jthemedetector;
    requires java.sql;
    requires bcrypt;

    opens no.ntnu.idatt1002.data;
    opens no.ntnu.idatt1002.controller;
    opens no.ntnu.idatt1002.data.economy;
    opens no.ntnu.idatt1002.util;
    opens no.ntnu.idatt1002.scene;
    opens no.ntnu.idatt1002.dao.sqlite;

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
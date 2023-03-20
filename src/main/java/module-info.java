module no.ntnu.idatt1002 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires com.jthemedetector;
    requires java.sql;
    requires bcrypt;

    exports no.ntnu.idatt1002.controller;
    exports no.ntnu.idatt1002.dao;
    exports no.ntnu.idatt1002;

}
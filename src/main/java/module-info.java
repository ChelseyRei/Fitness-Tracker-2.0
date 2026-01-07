module fitness.tracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    // CRITICAL: This allows JavaFX to see your UI Controllers
    opens controller to javafx.fxml;

    // Export your code so the rest of the app can use it
    exports model;
    exports dao;
    exports service;
    exports controller;
}
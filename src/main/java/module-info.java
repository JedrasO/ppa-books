module com.example.booksapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    requires java.sql;

    requires com.microsoft.sqlserver.jdbc;

    opens com.example.booksapp.models;
    opens com.example.booksapp.dbtest;
    opens com.example.booksapp;
    exports com.example.booksapp;
}
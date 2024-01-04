module com.example.movietickets {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.zaxxer.hikari;


    opens com.example.movietickets to javafx.fxml;
    exports com.example.movietickets;
}
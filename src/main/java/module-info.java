module com.example.movietickets {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.movietickets to javafx.fxml;
    exports com.example.movietickets;
}
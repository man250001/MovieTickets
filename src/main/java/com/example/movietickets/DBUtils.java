package com.example.movietickets;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class DBUtils {

    public static void changeScene(ActionEvent event, String fxmlFile, String title) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(DBUtils.class.getClassLoader().getResource(fxmlFile)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static boolean checkUsername(String username) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/movietickets", "root", "password");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public static boolean logInUser(ActionEvent event, String username, String password) throws SQLException, IOException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/movietickets", "root", "password");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            changeScene(event, "HomePage.fxml", "Home Menu");
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid username or password");
            alert.setContentText("Please try again");
            alert.showAndWait();
            return false;
        }
    }

    public static void signUpUser(ActionEvent event, String username, String password, String email) throws SQLException, IOException {
        if (checkUsername(username)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Username already exists");
            alert.setContentText("Please try again");
            alert.showAndWait();
            return;
        }
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/movietickets", "root", "password");
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (username, password, email) VALUES (?, ?, ?)");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, email);
        preparedStatement.executeUpdate();
        changeScene(event, "Login.fxml", "Login");
    }

    //Currently unused, will be modified/used in the future, thanks! 😊 -GitHub Copilot
    public static void addMovie(ActionEvent event, String title, String genre, String rating) throws SQLException, IOException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/movietickets", "root", "password");
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO movies (title, genre, runtime, releaseDate) VALUES (?, ?, ?, ?)");
        preparedStatement.setString(1, title);
        preparedStatement.setString(2, genre);
        preparedStatement.setString(3, rating);
    }
}

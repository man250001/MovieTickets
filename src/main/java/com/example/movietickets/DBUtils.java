package com.example.movietickets;

import com.zaxxer.hikari.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.regex.Pattern;

public class DBUtils {

    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        config.setJdbcUrl("jdbc:mysql://localhost:3306/movietickets");
        config.setUsername("root");
        config.setPassword("password");
        ds = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void changeScene(ActionEvent event, String fxmlFile, String title) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(DBUtils.class.getResource(fxmlFile)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static boolean checkUsername(String username) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE username = ?");
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-zA-Z.]{2,18}");
        return pattern.matcher(email).matches();
    }

    public static void logInUser(ActionEvent event, String username, String password) throws SQLException, IOException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            changeScene(event, "HomePage.fxml", "Home Menu");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid username or password");
            alert.setContentText("Please try again");
            alert.showAndWait();
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
        }else if (!validateEmail(email)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid email, must inclue @ and .(domain)");
            alert.setContentText("Please try again");
            alert.showAndWait();
            return;
        }
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user (username, password, email) VALUES (?, ?, ?)");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, email);
        preparedStatement.executeUpdate();
        changeScene(event, "Login.fxml", "Login");
    }

    //Currently unused, will be modified/used in the future, thanks! 😊 -GitHub Copilot
    public static void addMovie(ActionEvent event, String title, String genre, String rating) throws SQLException, IOException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO movies (title, genre, runtime, releaseDate) VALUES (?, ?, ?, ?)");
        preparedStatement.setString(1, title);
        preparedStatement.setString(2, genre);
        preparedStatement.setString(3, rating);
    }
}

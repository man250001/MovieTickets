package com.example.movietickets;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private PasswordField passwordBox;

    @FXML
    private Button signInButton;

    @FXML
    private Hyperlink signUpLink;

    @FXML
    private TextField usernameBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signInButton.setOnAction(actionEvent -> {
            try {
                DBUtils.logInUser(actionEvent, usernameBox.getText(), passwordBox.getText());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        signUpLink.setOnAction(actionEvent -> {
            try {
                DBUtils.changeScene(actionEvent, "signup.fxml", "Sign Up");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

}

package com.example.movietickets;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

@SuppressWarnings("unused")
public class SignUpController implements Initializable {

    @FXML
    private TextField emailBox, usernameBox;

    @FXML
    private Hyperlink logInLink;

    @FXML
    private PasswordField passwordBox;

    @FXML
    private Button signUpButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signUpButton.setOnAction(actionEvent -> {
            try {
                DBUtils.signUpUser(actionEvent, usernameBox.getText(), passwordBox.getText(), emailBox.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        logInLink.setOnAction(actionEvent -> {
            try {
                DBUtils.changeScene(actionEvent, "Login.fxml", "Login");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

package com.example.movietickets;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private AnchorPane addLeft, bottomPane, dashTop, editBottom, editTop, leftPane, movieRight, rightPane, ticketLeft, ticketRight, topPane, movieLeft;

    @FXML
    private Hyperlink addMovieLink, customersLink, dashLink, moviesLink, screeningLink, signOutLink;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dashLink.setOnAction(actionEvent -> {
            PaneDisabler();
            CompletelyEnable(dashTop);
            CompletelyEnable(topPane);
            CompletelyEnable(bottomPane);
        });
        moviesLink.setOnAction(actionEvent -> {
            PaneDisabler();
            CompletelyEnable(leftPane);
            CompletelyEnable(movieLeft);
            CompletelyEnable(rightPane);
            CompletelyEnable(movieRight);
        });
        screeningLink.setOnAction(actionEvent -> {
            PaneDisabler();
            CompletelyEnable(topPane);
            CompletelyEnable(bottomPane);
            CompletelyEnable(editBottom);
            CompletelyEnable(editTop);
        });
        customersLink.setOnAction(actionEvent -> {
            PaneDisabler();
            CompletelyEnable(leftPane);
            CompletelyEnable(ticketLeft);
            CompletelyEnable(rightPane);
            CompletelyEnable(ticketRight);
        });
        addMovieLink.setOnAction(actionEvent -> {
            PaneDisabler();
            CompletelyEnable(leftPane);
            CompletelyEnable(addLeft);
            CompletelyEnable(rightPane);
            CompletelyEnable(movieRight);
        });
        signOutLink.setOnAction(actionEvent -> {
            try {
                DBUtils.changeScene(actionEvent, "login.fxml", "Login");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void CompletelyDisable(Pane pane) {
        pane.setDisable(true);
        pane.setVisible(false);
    }

    public void CompletelyEnable(Pane pane) {
        pane.setDisable(false);
        pane.setVisible(true);
    }

    public void PaneDisabler() {
        CompletelyDisable(addLeft);
        CompletelyDisable(bottomPane);
        CompletelyDisable(dashTop);
        CompletelyDisable(editBottom);
        CompletelyDisable(editTop);
        CompletelyDisable(leftPane);
        CompletelyDisable(movieRight);
        CompletelyDisable(rightPane);
        CompletelyDisable(ticketLeft);
        CompletelyDisable(ticketRight);
        CompletelyDisable(topPane);
    }
}

package com.example.movietickets;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

@SuppressWarnings("unchecked")
public class HomeController implements Initializable {

    public int currentID = 0;

    @FXML
    private AnchorPane addLeft, bottomPane, dashTop, editBottom, editTop, leftPane, movieRight, rightPane, ticketLeft, ticketRight, topPane, movieLeft;

    @FXML
    private Hyperlink addMovieLink, customersLink, dashLink, moviesLink, screeningLink, signOutLink;

    @FXML
    private Button clearAdd, insertAdd, updateAdd, deleteAdd, importAdd;

    @FXML
    private TextField titleAdd, genreAdd, durationAdd;

    @FXML
    private TableView<Movie> movieList;

    @FXML
    private TableColumn<Movie, String> titleColAdd, genreColAdd, durationColAdd, publishedColAdd;

    @FXML
    private DatePicker dateAdd;

    @FXML
    private ImageView imageAdd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillAddMoviesTable();

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
                throw new RuntimeException(e);
            }
        });
        insertAdd.setOnAction(actionEvent -> {
            try {
                DBUtils.addMovie(actionEvent, titleAdd.getText(), genreAdd.getText(), durationAdd.getText(), java.sql.Date.valueOf(dateAdd.getValue()), imageAdd.getImage());
                fillAddMoviesTable();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        importAdd.setOnAction(actionEvent -> {
            imageAdd.setImage(ImportImage());
        });
        movieList.setOnMouseClicked(mouseEvent -> {
            Movie movie = movieList.getSelectionModel().getSelectedItem();
            currentID = movie.ID();
            titleAdd.setText(movie.title());
            genreAdd.setText(movie.genre());
            durationAdd.setText(movie.duration());
            dateAdd.setValue(movie.releaseDate().toLocalDate());
            imageAdd.setImage(movie.image());
        });
        deleteAdd.setOnAction(actionEvent -> {
            try {
                DBUtils.removeMovie(actionEvent, currentID);
                fillAddMoviesTable();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        updateAdd.setOnAction(actionEvent -> {
            try {
                DBUtils.updateMovie(actionEvent, currentID, titleAdd.getText(), genreAdd.getText(), durationAdd.getText(), java.sql.Date.valueOf(dateAdd.getValue()), imageAdd.getImage());
                fillAddMoviesTable();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        clearAdd.setOnAction(actionEvent -> {
            titleAdd.clear();
            genreAdd.clear();
            durationAdd.clear();
            dateAdd.setValue(null);
            imageAdd.setImage(null);
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
        CompletelyDisable(movieLeft);
        CompletelyDisable(movieRight);
        CompletelyDisable(rightPane);
        CompletelyDisable(ticketLeft);
        CompletelyDisable(ticketRight);
        CompletelyDisable(topPane);
    }

    public Image ImportImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
            new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imageAdd.setImage(image);
            return image;
        } else {
            return null;
        }
    }

    public void fillAddMoviesTable() {
        movieList.setItems(FXCollections.observableList(DBUtils.getAllMovies()));
        titleColAdd.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().title()));
        genreColAdd.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().genre()));
        durationColAdd.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().duration()));
        publishedColAdd.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().releaseDate().toString()));
        movieList.getColumns().setAll(titleColAdd, genreColAdd, durationColAdd, publishedColAdd);
    }
}

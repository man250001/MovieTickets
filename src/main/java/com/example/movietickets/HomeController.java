package com.example.movietickets;

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

    @FXML
    private AnchorPane addLeft, bottomPane, dashTop, editBottom, editTop, leftPane, movieRight, rightPane, ticketLeft, ticketRight, topPane, movieLeft;

    @FXML
    private Hyperlink addMovieLink, customersLink, dashLink, moviesLink, screeningLink, signOutLink;

    @FXML
    private Button clearAdd, insertAdd, updateAdd, deleteAdd, importAdd;

    @FXML
    private TextField titleAdd, genreAdd, durationAdd;

    @FXML
    private TableView<Movie> MovieList;

    @FXML
    private TableColumn<Movie, String> titleColAdd, genreColAdd, durationColAdd, dateColAdd;

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
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        importAdd.setOnAction(actionEvent -> {
            imageAdd.setImage(ImportImage());
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
        ArrayList<Movie> movies = DBUtils.getAllMovies();
        ArrayList<String> titles = new ArrayList<>(), genres = new ArrayList<>(), durations = new ArrayList<>();
        ArrayList<Date> dates = new ArrayList<>();
        if (movies.isEmpty()){
            return;
        }
        for (Movie movie : movies) {
            titles.add(movie.title());
            genres.add(movie.genre());
            durations.add(movie.duration());
            dates.add(movie.releaseDate());
        }
        titleColAdd.setCellValueFactory(new PropertyValueFactory<>("title"));
        genreColAdd.setCellValueFactory(new PropertyValueFactory<>("genre"));
        durationColAdd.setCellValueFactory(new PropertyValueFactory<>("duration"));
        dateColAdd.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        MovieList.getColumns().setAll(titleColAdd, genreColAdd, durationColAdd, dateColAdd);
    }
}

package com.example.movietickets;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
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
    private TableView<Ticket> ticketTable;

    @FXML
    private TableColumn<Ticket, String> tNumCol, tNameCol, tGenreCol, tDateCol, tTimeCol;

    @FXML
    private DatePicker dateAdd;

    @FXML
    private TableView<Movie> eMovieTable;

    @FXML
    private TableColumn<Movie, String> eMTitle, eMGenre, eMDate;

    @FXML
    private ImageView eImage;

    @FXML
    private Button tClear, tDelete;

    @FXML
    private TextField tID, tTitle, tGenre, tDate, tTime;

    @FXML
    private Button eClear, eBuy, eReceipt;

    @FXML
    private Spinner<Integer> eNormal, eSpecial;

    @FXML
    private Label eTotal, eNPrice, eSPrice, eTitleLabel, eGenreLabel;

    @FXML
    private ImageView imageAdd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillAddMoviesTable();
        fillEditMoviesTable();
        fillTicketTable();
        eNormal.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        eSpecial.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));

        ticketTable.setOnMouseClicked(mouseEvent -> {
            Ticket ticket = ticketTable.getSelectionModel().getSelectedItem();
            tID.setText(ticket.num() + "");
            tTitle.setText(ticket.movie());
            tGenre.setText(ticket.genre());
            tDate.setText(ticket.date().toString());
            tTime.setText(ticket.time().toString());
        });
        tClear.setOnAction(actionEvent -> {
            tID.clear();
            tTitle.clear();
            tGenre.clear();
            tDate.clear();
            tTime.clear();
        });
        tDelete.setOnAction(actionEvent -> {
            DBUtils.deleteTicket(actionEvent, Integer.parseInt(tID.getText()));
            fillTicketTable();
            tClear.fire();
        });
        eMovieTable.setOnMouseClicked(mouseEvent -> {
            Movie movie = eMovieTable.getSelectionModel().getSelectedItem();
            eTitleLabel.setText(movie.title());
            eGenreLabel.setText(movie.genre());
            eImage.setImage(movie.image());
            eSpecial.setDisable(false);
            eNormal.setDisable(false);
            eBuy.setDisable(false);
        });
        eClear.setOnAction(actionEvent -> {
            eTitleLabel.setText("");
            eGenreLabel.setText("");
            eImage.setImage(null);
            eSpecial.setDisable(true);
            eNormal.setDisable(true);
            eBuy.setDisable(true);
            eSpecial.getValueFactory().setValue(0);
            eNormal.getValueFactory().setValue(0);
            eTotal.setText("$0.00");
            eNPrice.setText("$0.00");
            eSPrice.setText("$0.00");
        });
        eBuy.setOnAction(actionEvent -> {
            if (eSpecial.getValue() != 0){
                DBUtils.addTransaction(actionEvent, "Special", eTitleLabel.getText(), eSpecial.getValue(), Double.parseDouble(eSPrice.getText().substring(1)), new java.sql.Date(System.currentTimeMillis()), new java.sql.Time(System.currentTimeMillis()));
            }
            if (eNormal.getValue() != 0){
                DBUtils.addTransaction(actionEvent, "Normal", eTitleLabel.getText(), eNormal.getValue(), Double.parseDouble(eNPrice.getText().substring(1)), new java.sql.Date(System.currentTimeMillis()), new java.sql.Time(System.currentTimeMillis()));
            }
            eClear.fire();
        });
        eSpecial.setOnMouseClicked(mouseEvent -> {
            eSPrice.setText("$" + (eSpecial.getValue() * 10) + ".00");
            eTotal.setText("$" + (eSpecial.getValue() * 10 + eNormal.getValue() * 5) + ".00");
        });
        eNormal.setOnMouseClicked(mouseEvent -> {
            eNPrice.setText("$" + (eNormal.getValue() * 5) + ".00");
            eTotal.setText("$" + (eSpecial.getValue() * 10 + eNormal.getValue() * 5) + ".00");
        });
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
        importAdd.setOnAction(actionEvent -> imageAdd.setImage(ImportImage()));
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

    public void fillEditMoviesTable(){
        eMovieTable.setItems(FXCollections.observableList(DBUtils.getAllMovies()));
        eMTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().title()));
        eMGenre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().genre()));
        eMDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().releaseDate().toString()));
        eMovieTable.getColumns().setAll(eMTitle, eMGenre, eMDate);
    }

    public void fillAddMoviesTable() {
        movieList.setItems(FXCollections.observableList(DBUtils.getAllMovies()));
        titleColAdd.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().title()));
        genreColAdd.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().genre()));
        durationColAdd.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().duration()));
        publishedColAdd.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().releaseDate().toString()));
        movieList.getColumns().setAll(titleColAdd, genreColAdd, durationColAdd, publishedColAdd);
    }

    public void fillTicketTable() {
        ticketTable.setItems(FXCollections.observableList(DBUtils.getTickets()));
        tNumCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().num() + ""));
        tNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().movie()));
        tGenreCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().genre()));
        tDateCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().date().toString()));
        tTimeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().time().toString()));
        ticketTable.getColumns().setAll(tNumCol, tNameCol, tGenreCol, tDateCol, tTimeCol);
    }
}

package com.example.movietickets;
import javafx.scene.image.Image;
import java.sql.Date;
public record Movie(int ID, String title, String genre, String duration, Date releaseDate, Image image) {
}
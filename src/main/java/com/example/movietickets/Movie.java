package com.example.movietickets;
import javafx.scene.image.Image;
import java.sql.Date;
public record Movie(String title, String genre, String duration, Date releaseDate, Image image) {
}
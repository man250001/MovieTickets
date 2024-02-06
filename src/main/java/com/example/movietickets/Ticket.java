package com.example.movietickets;

import java.sql.Date;
import java.sql.Time;

public record Ticket(int num, String movie, String genre, Date date, Time time) {
}

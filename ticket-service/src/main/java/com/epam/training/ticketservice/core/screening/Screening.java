package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.Movie;
import com.epam.training.ticketservice.core.room.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Screenings")
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_title")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "room_name")
    private Room room;

    private LocalDateTime date;

    public Screening(Movie movie, Room room, LocalDateTime date) {
        this.movie = movie;
        this.room = room;
        this.date = date;
    }

    public Movie getMovie() {
        return movie;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return movie.getTitle() + " (" + movie.getGenre() + ", " + movie.getLength() + " minutes), screened in room " + room.getName() + ", at " + date.toString().replace("T", " ");
    }
}
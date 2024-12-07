package com.epam.training.ticketservice.core.movie;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Builder
@Table(name = "Movies")
public class Movie {

    @Id
    @Column(unique=true)
    private String title;

    private String genre;

    private int length;

    public Movie(String title, String genre, int length) {
        this.title = title;
        this.genre = genre;
        this.length = length;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return title + " (" + genre + ", " + length + " minutes)";
    }
}

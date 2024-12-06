package com.epam.training.ticketservice.core.room;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Builder
@Table(name = "Rooms")
public class Room {

    @Id
    private String name;
    private int rows;
    private int cols;

    public Room(String name, int rows, int cols) {
        this.name = name;
        this.rows = rows;
        this.cols = cols;
    }

    public String getName() {
        return name;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    @Override
    public String toString() {
        return "Room " + name + " with " + rows * cols + " seats, " + rows + " rows and " + cols + " columns";
    }
}

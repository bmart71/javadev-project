package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.Movie;
import com.epam.training.ticketservice.core.room.Room;

import java.time.LocalDateTime;
import java.util.List;

public interface ScreeningService {
    void createScreening(Screening screening);
    List<Screening> getAllScreenings();
    void deleteScreening(Long ScreeningId);
    Screening getScreeningByMovieTitleAndRoomNameAndScreeningTime(Movie movie, Room room, LocalDateTime time);
}

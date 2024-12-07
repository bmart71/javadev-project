package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.exceptions.ScreeningServiceException;
import com.epam.training.ticketservice.core.movie.Movie;
import com.epam.training.ticketservice.core.room.Room;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScreeningServiceImpl implements ScreeningService {

    private final ScreeningRepository screeningRepository;

    public ScreeningServiceImpl(ScreeningRepository screeningRepository) {
        this.screeningRepository = screeningRepository;
    }

    @Override
    public void createScreening(Screening screening) throws ScreeningServiceException {
        List<Screening> existingScreenings = screeningRepository.findScreeningByRoom(screening.getRoom());

        LocalDateTime newStart = screening.getDate();
        LocalDateTime newEnd = screening.getDate().plusMinutes(screening.getMovie().getLength());

        for (Screening existing: existingScreenings) {
            LocalDateTime existingStart = existing.getDate();
            LocalDateTime existingEnd = existingStart.plusMinutes(existing.getMovie().getLength());
            LocalDateTime existingEndPlusBreak = existingEnd.plusMinutes(10);

            if (!(newEnd.isBefore(existingStart) || newStart.isAfter(existingEnd)))
                throw new ScreeningServiceException("There is an overlapping screening");

            if (newStart.isAfter(existingEnd) && newStart.isBefore(existingEndPlusBreak))
                throw new ScreeningServiceException("This would start in the break period after another screening in this room");
        }
        screeningRepository.save(screening);
    }

    @Override
    public List<Screening> getAllScreenings() {
        return screeningRepository.findAll();
    }

    @Override
    public void deleteScreening(Long ScreeningId){
        screeningRepository.deleteById(ScreeningId);
    }

    @Override
    public Screening getScreeningByMovieTitleAndRoomNameAndScreeningTime(Movie movie, Room room, LocalDateTime time) {
        return screeningRepository.findScreeningByRoomAndMovieAndDate(room, movie, time);
    }
}

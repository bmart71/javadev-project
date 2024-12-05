package com.epam.training.ticketservice.core.movie;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MovieRepository extends CrudRepository<Movie, Long> {
    Optional<Movie> findByTitle(String title);
}

package com.rindus.bookingsystem.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rindus.bookingsystem.model.Status;

/**
 * Repository of {@link BookingEntity} entities.
 */
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    /**
     * Returns all booking entities with a specific status
     * 
     * @param status of the booking
     * @return list of BookingEntity
     */
    List<BookingEntity> findAllByStatus(Status status);

}

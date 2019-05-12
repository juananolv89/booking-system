package com.rindus.bookingsystem.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.rindus.bookingsystem.controller.BookingController.Action;
import com.rindus.bookingsystem.jpa.BookingEntity;
import com.rindus.bookingsystem.jpa.BookingRepository;
import com.rindus.bookingsystem.model.Status;

import lombok.extern.slf4j.Slf4j;

/**
 * Service in charge of providing an efficient manage of bookings entities.
 * <p>
 * It acts as a wrapper between the JPA repository and the controller.
 * <p>
 * Note that the service is not reentrant since two different threads could try
 * to modify the same entity at the same time but in different transactions. In
 * this case, only the first transaction will success.
 */
@Slf4j
@Service
public class BookingService {

    /** Minutes after a block booking is released */
    @Value("${job.unblock.orphan.bookings.minutes}")
    private Integer orphanBlockThresholdMinutes;

    /** Repository of {@link BookingEntity} objects */
    private final BookingRepository repo;

    /**
     * Constructor.
     * 
     * @param repo BookingRepository
     */
    public BookingService(BookingRepository repo) {
	log.debug("Creating the BookingService service");

	this.repo = repo;
    }

    /**
     * Get all current available bookings.
     *
     * @return a {@link List} of {@link BookingEntity} with available status
     */
    public List<BookingEntity> findAllAvailables() {
	log.debug("Loading available bookings.");
	return repo.findAllByStatus(Status.AVAILABLE);
    }

    /**
     * Update status of a given booking
     *
     * @param id     of the booking entity
     * @param action to perform
     */
    @Transactional
    public void updateStatusBooking(long id, Action action) {
	Objects.requireNonNull(id, "id cannnot be null");
	Objects.requireNonNull(action, "action cannnot be null");

	log.debug("Updating booking id {}, with action {}.", id, action);
	Optional<BookingEntity> entityOptional = repo.findById(id);

	if (!entityOptional.isPresent()) {
	    throw new ResourceNotFoundException("Provided booking id not found");
	}

	BookingEntity entity = entityOptional.get();

	switch (action) {
	case BLOCK:
	    if (entity.getStatus() == Status.AVAILABLE) {
		entity.setStatus(Status.BLOCKED);
	    } else {
		throw new WrongActionException("Provided booking id cannot be blocked, it is not available.");
	    }
	    break;
	case CONFIRM:
	    if (entity.getStatus() == Status.BLOCKED) {
		entity.setStatus(Status.CONFIRMED);
	    } else {
		throw new WrongActionException("Provided booking id cannot be confirmed, it is not blocked.");
	    }
	    break;
	case CANCEL:
	    if (entity.getStatus() != Status.AVAILABLE) {
		entity.setStatus(Status.AVAILABLE);
	    } else {
		throw new WrongActionException("Provided booking id cannot be cancel, it is already available.");
	    }
	    break;
	}
	entity.setLastUpdateTime(LocalDateTime.now());
	repo.save(entity);
    }

    /**
     * Job in charge to release orphan block bookings. Job is executed periodically
     * based on the minutes configured in the application properties file.
     * 
     */
    @Scheduled(initialDelayString = "${job.unblock.orphan.bookings.period}", fixedRateString = "${job.unblock.orphan.bookings.period}")
    public void unblockOrfandBookings() {

	log.debug("Executing unblock orfand bookings job.");
	LocalDateTime dateThreshold = LocalDateTime.now().minusMinutes(orphanBlockThresholdMinutes);

	repo.findAllByStatus(Status.BLOCKED).stream().filter(b -> b.getLastUpdateTime().isBefore(dateThreshold))
		.forEach(this::releaseBooking);

    }

    /**
     * Release a booking entity, in other words change status to available.
     * 
     * @param entity to release
     */
    @Transactional
    private void releaseBooking(BookingEntity entity) {
	Objects.requireNonNull(entity, "entity cannnot be null");

	entity.setStatus(Status.AVAILABLE);
	entity.setLastUpdateTime(LocalDateTime.now());
	repo.save(entity);
    }

}

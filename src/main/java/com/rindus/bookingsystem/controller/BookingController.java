package com.rindus.bookingsystem.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rindus.bookingsystem.dto.BookingDto;
import com.rindus.bookingsystem.jpa.BookingEntity;
import com.rindus.bookingsystem.service.BookingService;

import lombok.extern.slf4j.Slf4j;

/**
 * End points used by the clients to manage bookings.
 */
@Slf4j
@RestController
public class BookingController {

    private static final String BLOKING_BOOKING_ID_DEBUG_MESSAGE = "Bloking booking id {}.";

    /**
     * Service in charge of handling the bookings
     */
    private BookingService bookingService;

    /**
     * ModelMapper in charge of mapping entities to DTOs data objects
     */
    private ModelMapper modelMapper;

    /** Possible actions with a booking */
    public enum Action {
	BLOCK, CONFIRM, CANCEL
    }

    /**
     * Constructor
     * 
     * @param bookingService BookingService
     * @param modelMapper    ModelMapper
     */
    @Autowired
    BookingController(BookingService bookingService, ModelMapper modelMapper) {
	this.bookingService = bookingService;
	this.modelMapper = modelMapper;
    }

    /**
     * Get available bookings end point.
     * 
     * @param req low-level information about the request
     * @return response body
     */
    @GetMapping("/bookings")
    public List<BookingDto> allAvailables(HttpServletRequest req) {
	log.debug("Loading availables bookings.");
	return bookingService.findAllAvailables().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /**
     * Block a booking end point.
     * 
     * @param req low-level information about the request
     * @return response body
     */
    @PostMapping("/bookings/{id}/block")
    public void blockBooking(HttpServletRequest req, @PathVariable(required = true) Long id) {
	log.debug(BLOKING_BOOKING_ID_DEBUG_MESSAGE, id);
	bookingService.updateStatusBooking(id, Action.BLOCK);
    }

    /**
     * Confirm a booking end point.
     * 
     * @param req low-level information about the request
     * @return response body
     */
    @PostMapping("/bookings/{id}/confirm")
    public void confirmBooking(HttpServletRequest req, @PathVariable(required = true) Long id) {
	log.debug(BLOKING_BOOKING_ID_DEBUG_MESSAGE, id);
	bookingService.updateStatusBooking(id, Action.CONFIRM);
    }

    /**
     * Cancel a booking end point.
     * 
     * @param req low-level information about the request
     * @return response body
     */
    @PostMapping("/bookings/{id}/cancel")
    public void cancelBooking(HttpServletRequest req, @PathVariable(required = true) Long id) {
	log.debug(BLOKING_BOOKING_ID_DEBUG_MESSAGE, id);
	bookingService.updateStatusBooking(id, Action.CANCEL);
    }

    /**
     * Helper method to convert from {@link BookingEntity} to {@link BookingDto}
     * 
     * @param booking entity data object
     * @return bookingDto data object
     */
    private BookingDto convertToDto(BookingEntity booking) {
	return modelMapper.map(booking, BookingDto.class);
    }

}

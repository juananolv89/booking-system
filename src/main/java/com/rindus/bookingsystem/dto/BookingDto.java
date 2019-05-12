package com.rindus.bookingsystem.dto;

import java.time.LocalDateTime;

import com.rindus.bookingsystem.model.Status;

import lombok.Getter;
import lombok.Setter;

/**
 * Dto class to transfer booking data
 */
@Getter
@Setter
public class BookingDto {

    /**
     * Id of the booking
     */
    private long id;

    /**
     * Status of the booking
     */
    private Status status;

    /**
     * Start time of the booking
     */
    private LocalDateTime startTime;

    /**
     * End time of the booking
     */
    private LocalDateTime endTime;

    /**
     * Room of the booking
     */
    private RoomDto room;

}

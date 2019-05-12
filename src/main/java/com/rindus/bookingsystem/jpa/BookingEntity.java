package com.rindus.bookingsystem.jpa;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.LastModifiedDate;

import com.rindus.bookingsystem.model.Status;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Information stored in the database about a booking.
 */
@Entity
@Table(name = "booking")
@Getter
@Setter
@ToString
public class BookingEntity {

    /**
     * Database id automatically assigned
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Status of the booking
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * Start time of the booking
     */
    @NotNull
    private LocalDateTime startTime;

    /**
     * End time of the booking
     */
    @NotNull
    private LocalDateTime endTime;

    /**
     * Last change in the booking
     */
    @NotNull
    @LastModifiedDate
    private LocalDateTime lastUpdateTime;

    /**
     * Room Id of the associated room
     */
    @NotNull
    @ManyToOne
    @Fetch(FetchMode.JOIN)
    private RoomEntity room;

    /**
     * No-arguments constructor (required by JPA)
     */
    protected BookingEntity() {
    }

    /**
     * Create a new booking entity.
     * <p>
     * Note that after creating this bean, its has not an Id assigned (therefore, it
     * is not a valid bean).
     * 
     * @param status         of the booking
     * @param startTime      of the booking
     * @param endTime        of the booking
     * @param lastUpdateTime last update time of the booking
     * @param room           room entity associate to the booking
     */
    public BookingEntity(@NonNull Status status, @NonNull LocalDateTime startTime, @NonNull LocalDateTime endTime,
	    LocalDateTime lastUpdateTime, @NonNull RoomEntity room) {
	this.status = status;
	this.startTime = startTime;
	this.endTime = endTime;
	this.lastUpdateTime = lastUpdateTime;
	this.room = room;
    }
}

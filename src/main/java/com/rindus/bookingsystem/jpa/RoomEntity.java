package com.rindus.bookingsystem.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * Information stored in the database about a booking.
 */
@Entity
@Table(name = "room")
@Getter
@Setter
@ToString
public class RoomEntity {

    /**
     * Database id automatically assigned
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * status of the booking
     */
    @NotNull
    @Size(max = 255)
    private String name;

    /**
     * No-arguments constructor (required by JPA)
     */
    protected RoomEntity() {
    }

    /**
     * Create a new room entity.
     * <p>
     * Note that after creating this bean, its has not an Id assigned (therefore, it
     * is not a valid bean).
     * 
     * @param name of the room
     */
    public RoomEntity(@NonNull String name) {
	this.name = name;
    }
}

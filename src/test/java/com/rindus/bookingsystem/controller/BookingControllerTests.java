package com.rindus.bookingsystem.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.rindus.bookingsystem.dto.BookingDto;
import com.rindus.bookingsystem.model.Status;
import com.rindus.bookingsystem.service.ResourceNotFoundException;
import com.rindus.bookingsystem.service.WrongActionException;

/**
 * Unit tests for the {@link BookingController} class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookingControllerTests {

    /** Mock of the ModelMapper to be used during the test */
    @Mock
    private HttpServletRequest req;

    /** Unit under test */
    @Autowired
    private BookingController controller;

    /** Minutes after a block booking is released */
    @Value("${job.unblock.orphan.bookings.minutes}")
    private Integer orphanBlockThresholdMinutes;

    @Value("${job.unblock.orphan.bookings.period}")
    private Integer period;

    /**
     * Test the get bookings functionality of the controller (success case).
     */
    @Test
    public void getBookingsSuccess() {
	List<BookingDto> result = controller.allAvailables(req);

	// Check that the list is not empty
	assertThat(result.isEmpty(), is(false));

	// Check that all fields of first row are filled
	BookingDto row1 = result.get(0);
	assertTrue(row1.getStatus() == Status.AVAILABLE);
	assertNotNull(row1.getStartTime());
	assertNotNull(row1.getEndTime());
	assertNotNull(row1.getRoom());
    }

    /**
     * Test the block Booking functionality of the controller (success case).
     */
    @Test
    public void blockBookingSuccess() {

	BookingDto element = controller.allAvailables(req).stream().filter(booking -> booking.getId() == 1).findFirst()
		.get();
	assertNotNull(element);
	controller.blockBooking(req, 1L);
	List<BookingDto> result = controller.allAvailables(req);
	assertNotNull(result);
	// Only available items are returned
	assertThat(result, not(hasItem(element)));
    }

    /**
     * Test the block Booking functionality of the controller (error case). No
     * booking resource
     */
    @Test(expected = ResourceNotFoundException.class)
    public void blockBookingErrorNotFound() {
	controller.blockBooking(req, 1000L);
    }

    /**
     * Test the block booking functionality of the controller (error case). Wrong
     * status
     */
    @Test(expected = WrongActionException.class)
    public void blockBookingErroWrongAction() {

	// Try to block two times the same booking
	controller.blockBooking(req, 4L);
	controller.blockBooking(req, 4L);
    }

    /**
     * Test the confirm booking functionality of the controller (success case).
     */
    @Test
    public void confirmAndCancelBookingSuccess() {

	BookingDto element = controller.allAvailables(req).stream().filter(booking -> booking.getId() == 2).findFirst()
		.get();
	assertNotNull(element);
	// first block
	controller.blockBooking(req, 2L);
	// then confirm
	controller.confirmBooking(req, 2L);
	List<BookingDto> result = controller.allAvailables(req);
	assertNotNull(result);
	// Only available items are returned thus id 2 must not be present
	assertThat(result, not(hasItem(element)));
	// Once cancel it must be available again
	controller.cancelBooking(req, 2L);
	result = controller.allAvailables(req);
	assertNotNull(result);
	// Only available items are returned
	element = controller.allAvailables(req).stream().filter(booking -> booking.getId() == 2).findFirst().get();
	assertNotNull(element);
    }

    /**
     * Test the block booking functionality of the controller (success case). Check
     * that a block booking is released after a certain period.
     * 
     * Note the execution of this test can takes several minutes.
     * 
     * @throws InterruptedException
     */
    @Test
    public void blockReleaseBookingSuccess() throws InterruptedException {

	BookingDto element = controller.allAvailables(req).stream().filter(booking -> booking.getId() == 3).findFirst()
		.get();
	assertNotNull(element);
	controller.blockBooking(req, 3L);
	List<BookingDto> result = controller.allAvailables(req);
	assertNotNull(result);
	// Only available items are returned
	assertThat(result, not(hasItem(element)));

	// Wait until the booking is released automatically based on time configured in
	// properties
	TimeUnit.MINUTES.sleep(orphanBlockThresholdMinutes);
	TimeUnit.MILLISECONDS.sleep(period);

	result = controller.allAvailables(req);
	assertNotNull(result);
	BookingDto element2 = controller.allAvailables(req).stream().filter(booking -> booking.getId() == 3).findFirst()
		.get();
	assertNotNull(element2);
    }

}

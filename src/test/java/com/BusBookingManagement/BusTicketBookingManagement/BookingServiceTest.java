package com.BusBookingManagement.BusTicketBookingManagement;

import com.BusBookingManagement.BusTicketBookingManagement.Entity.Booking;
import com.BusBookingManagement.BusTicketBookingManagement.Entity.Bus;
import com.BusBookingManagement.BusTicketBookingManagement.Entity.Passengers;
import com.BusBookingManagement.BusTicketBookingManagement.Repository.BookingRepository;
import com.BusBookingManagement.BusTicketBookingManagement.Service.BookingService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    @Test
    public void testSaveBooking() {
        Passengers passenger = new Passengers("John Doe", "john@example.com", "password123", "USER");
        Bus bus = new Bus(1, "Bus Name", "From", "To", 100.0, LocalDate.now(), "10:00", "12:00");
        Booking booking = new Booking();
        booking.setPassengers(passenger);
        booking.setBusEntity(bus);
        booking.setNoOfPassengers(1);
        booking.setPaymentMode("Cash");

        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking savedBooking = bookingService.saveBooking(booking);

        verify(bookingRepository, times(1)).save(any(Booking.class));
        assertEquals(passenger, savedBooking.getPassengers());
        assertEquals(bus, savedBooking.getBusEntity());
        assertEquals(1, savedBooking.getNoOfPassengers());
        assertEquals("Cash", savedBooking.getPaymentMode());
    }
}


package com.BusBookingManagement.BusTicketBookingManagement.Service;

import com.BusBookingManagement.BusTicketBookingManagement.Entity.Booking;
import com.BusBookingManagement.BusTicketBookingManagement.Entity.PassengerDetail;
import com.BusBookingManagement.BusTicketBookingManagement.Entity.Passengers;
import com.BusBookingManagement.BusTicketBookingManagement.Repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    //Method to save ticket Booking
    public Booking saveBooking(Booking booking){
        return bookingRepository.save(booking);
    }

    //Method to show All Bookings
    public List<Booking> getAllBookings(){
            return bookingRepository.findAll();
    }

    //Method to findBy BookingId
        public Booking getByBookingId(int bookingId){
        return bookingRepository.findByBookingId(bookingId).orElse(null);
    }

    //Method to get their ticket by credential
    public List<Booking> getBookingByPassenger(Passengers passengers){
        return bookingRepository.findByPassengers(passengers);
    }

    // Method to save booking details with multiple passengers
    public Booking saveBookingWithPassengerDetails(Booking booking, List<PassengerDetail> passengerDetails) {
        for (PassengerDetail detail : passengerDetails) {
            detail.setBooking(booking);
        }
        booking.setPassengerDetails(passengerDetails);
        return bookingRepository.save(booking);
    }

}

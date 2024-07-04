package com.BusBookingManagement.BusTicketBookingManagement.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "Booking_Details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Booking_Id")
    private int bookingId;

    @ManyToOne
    @JoinColumn(name = "bus_Id",referencedColumnName = "bus_Id")
    private Bus busEntity;

    @ManyToOne
    @JoinColumn(name = "Passenger_Id",referencedColumnName = "Passenger_Id")
    private Passengers passengers;

    @OneToMany(mappedBy = "booking",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<PassengerDetail> passengerDetails;

    @Column(name = "Booking_Date",nullable = false)
    private Date bookingDate;

    @Column(name="Payment_Mode")
    private String paymentMode;

    @Column(name = "no_Of_Passengers")
    private int noOfPassengers;

    // Add helper method to add a passenger detail
    public void addPassengerDetail(PassengerDetail detail) {
        detail.setBooking(this);
        this.passengerDetails.add(detail);
    }
}

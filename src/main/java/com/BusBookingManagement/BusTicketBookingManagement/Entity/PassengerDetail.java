package com.BusBookingManagement.BusTicketBookingManagement.Entity;


//This class indicates to store Travel passenger Information


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Passenger_Details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Detail_Id")
    private int detailId;

    @ManyToOne
    @JoinColumn(name = "Booking_Id", referencedColumnName = "Booking_Id")
    private Booking booking;

    @Column(name = "travel_PassengerName")
    private String travelPassengerName;

    @Column(name = "Gender")
    private String travelPassengerGender;

    @Column(name = "Age",nullable = false)
    private int travelPassengerAge=0;

    @Column(name = "seat_Preference")
    private String seatPreference;
}


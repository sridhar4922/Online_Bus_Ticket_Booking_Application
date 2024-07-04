package com.BusBookingManagement.BusTicketBookingManagement.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



@Entity
@Table(name = "BusDetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serial_No")
    private int serialNo;


    @Column(name = "bus_Id",unique = true,nullable = false)
    private int busId;

    @Column(name = "bus_Name")
    private String busName;

    @Column(name = "departureLocation")
    private String from;

    @Column(name = "arrivalLocation")
    private String to;

    @Column(name = "ticketFar")
    private Double ticketFar;

    @Column(name = "checkingDate")
    private LocalDate checkingDate;

    @Column(name = "departureTime")
    private String departureTime;

    @Column(name = "arrivalTime")
    private String arrivalTime;


    public Bus(int i, String busName, String from, String to, double v, LocalDate now, String time, String time1) {
    }
}
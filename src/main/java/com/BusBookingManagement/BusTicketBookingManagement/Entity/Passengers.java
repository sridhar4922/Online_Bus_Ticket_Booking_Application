package com.BusBookingManagement.BusTicketBookingManagement.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Passengers", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Passengers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Passenger_Id")
    private Long passengerId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email",unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    //Zero Argument Constructor
    public Passengers(){
        super();
    }

    //Parameterized Constructor
    public Passengers(String fullName, String email, String password, String role) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}

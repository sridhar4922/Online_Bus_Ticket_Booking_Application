package com.BusBookingManagement.BusTicketBookingManagement.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    //Creating field from Passenger Class
    private String fullName;
    private String email;
    private String password;
    private String role;

    //Constructor
    public UserDto(String fullName, String email, String password, String role) {
        super();
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}

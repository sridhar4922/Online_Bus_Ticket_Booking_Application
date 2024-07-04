package com.BusBookingManagement.BusTicketBookingManagement.Service;

import com.BusBookingManagement.BusTicketBookingManagement.DTO.UserDto;
import com.BusBookingManagement.BusTicketBookingManagement.Entity.Passengers;

public interface UserService {

    //to collect the passenger information from Entity to Database
    Passengers save (UserDto userDto);
}

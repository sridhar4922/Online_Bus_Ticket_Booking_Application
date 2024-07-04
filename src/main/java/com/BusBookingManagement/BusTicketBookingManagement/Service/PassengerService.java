package com.BusBookingManagement.BusTicketBookingManagement.Service;

import com.BusBookingManagement.BusTicketBookingManagement.DTO.UserDto;
import com.BusBookingManagement.BusTicketBookingManagement.Entity.Passengers;
import com.BusBookingManagement.BusTicketBookingManagement.Repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PassengerService implements UserService {

    //fetch the passengers repository
    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //override the save method from UserService Interface
    @Override
    public Passengers save(UserDto userDto) {

        //to save passenger information to database
        Passengers user=new Passengers();
                user.setFullName(userDto.getFullName());
                user.setEmail(userDto.getEmail());
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                user.setRole(userDto.getRole());
        return passengerRepository.save(user);
    }

    //Method to check Current Logger
    public Passengers getCurrentLoggedInPassenger(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return passengerRepository.findByEmail(username);
        } else {
            return null;
        }
    }

    //Method to getById
    public Passengers getById(long passengerId){
        return  passengerRepository.getByPassengerId(passengerId).orElse(null);
    }
}

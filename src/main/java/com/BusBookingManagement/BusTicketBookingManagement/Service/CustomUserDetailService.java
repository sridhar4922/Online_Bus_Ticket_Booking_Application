package com.BusBookingManagement.BusTicketBookingManagement.Service;

import com.BusBookingManagement.BusTicketBookingManagement.Entity.Passengers;
import com.BusBookingManagement.BusTicketBookingManagement.Repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private PassengerRepository passengerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Passengers passengers=passengerRepository.findByEmail(username);
        if(passengers==null){
            throw new UsernameNotFoundException("User Credential Not Found...!");
        }
        return new CustomUserDetails(passengers);
    }
}

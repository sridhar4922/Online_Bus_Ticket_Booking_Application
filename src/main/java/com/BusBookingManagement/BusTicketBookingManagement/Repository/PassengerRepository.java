package com.BusBookingManagement.BusTicketBookingManagement.Repository;

import com.BusBookingManagement.BusTicketBookingManagement.Entity.Passengers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passengers,Long> {

    //Method to find passengers by Mail
    Passengers findByEmail(String email);

    //Method to find passenger by their PassengerId
    Optional<Passengers> getByPassengerId(Long passengerId);

}

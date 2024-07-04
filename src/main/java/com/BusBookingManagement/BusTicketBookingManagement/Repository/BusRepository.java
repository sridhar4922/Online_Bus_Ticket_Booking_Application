package com.BusBookingManagement.BusTicketBookingManagement.Repository;

import com.BusBookingManagement.BusTicketBookingManagement.Entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BusRepository extends JpaRepository<Bus,Integer> {

        //Method to find bus by Id
        Optional<Bus> findByBusId(int busId);

        //Method to delete bus Serial Id
        Optional<Bus>findBySerialNo(int serialNo);

        //Method to findBy from,to,date
        List<Bus> findByFromAndToAndCheckingDate(String from, String to, LocalDate checkingDate);

}

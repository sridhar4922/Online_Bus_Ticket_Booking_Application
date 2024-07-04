package com.BusBookingManagement.BusTicketBookingManagement.Service;


import com.BusBookingManagement.BusTicketBookingManagement.Entity.Bus;
import com.BusBookingManagement.BusTicketBookingManagement.Repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;


    //Method to save Bus
    public Bus saveBus(Bus bus){
        return busRepository.save(bus);
    }

    //Method to display bus
    public List<Bus> getAllBus(){
        return busRepository.findAll();
    }

    //Method to findBusById
    public Bus getBusById(int busId){
        return  busRepository.findByBusId(busId).orElse(null);
    }

    //Method to getBus by id Optional
    public Optional<Bus> getBusByIdOptional(int busId){
        return busRepository.findByBusId(busId);
    }

    //Method to Update Bus
    public Bus updateBus(Bus busUpdate){
        Optional<Bus> existingBus=busRepository.findByBusId(busUpdate.getBusId());

        if(existingBus.isPresent()){
            Bus currentBus=existingBus.get();
            currentBus.setBusId(busUpdate.getBusId());
            currentBus.setBusName(busUpdate.getBusName());
            currentBus.setFrom(busUpdate.getFrom());
            currentBus.setTo(busUpdate.getTo());
            currentBus.setTicketFar(busUpdate.getTicketFar());
            currentBus.setCheckingDate(busUpdate.getCheckingDate());
            currentBus.setDepartureTime(busUpdate.getDepartureTime());
            currentBus.setArrivalTime(busUpdate.getArrivalTime());

            return  busRepository.save(currentBus);
        }else{
            throw new RuntimeException("Bus not found with this ID:"+busUpdate.getBusId());
        }

    }

    // Method to delete Bus by serial number
    public void deleteBus(int serialNo) {
        Optional<Bus> bus = busRepository.findBySerialNo(serialNo);
        if (bus.isPresent()) {
            busRepository.delete(bus.get());
        } else {
            throw new RuntimeException("Bus not found with serial number: " + serialNo);
        }
    }

    //Method to filter bus by from,to,Date for booking entity
    public List<Bus> findBuses(String from, String to, LocalDate checkingDate){
        return busRepository.findByFromAndToAndCheckingDate(from,to,checkingDate);
    }





}
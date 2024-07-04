package com.BusBookingManagement.BusTicketBookingManagement.Controller;




import com.BusBookingManagement.BusTicketBookingManagement.Entity.Bus;
import com.BusBookingManagement.BusTicketBookingManagement.Entity.Passengers;
import com.BusBookingManagement.BusTicketBookingManagement.Service.BookingService;
import com.BusBookingManagement.BusTicketBookingManagement.Service.BusService;
import com.BusBookingManagement.BusTicketBookingManagement.Service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

//this controller only admin can access
@Controller
public class BusController {

    @Autowired
    private BusService busService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BookingService bookingService;

    //Method to get Admin Page
    @GetMapping("/admin")
    public String adminPage(Model model, Principal principal){
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(principal.getName());
        Passengers passengers=userDetails.getPassengers();
        model.addAttribute("passenger",passengers);
        return "admin-page";
    }

    //method to show form for Bus
    @GetMapping("/addBus")
    public String busRegister(){
        return "addBus";
    }

    //method to save bus into Database
    @PostMapping(value = "/saveBus")
    public String saveBus(@ModelAttribute Bus bus,Model model,Principal principal){
        CustomUserDetails userDetails=(CustomUserDetails)userDetailsService.loadUserByUsername(principal.getName());
        Passengers passengers=userDetails.getPassengers();
        model.addAttribute("passenger",passengers);
        busService.saveBus(bus);
        return "admin-page";
    }

    //Method to displayAllBus
    @GetMapping("/viewAllBuses")
    public String displayAllBus(Model model){
        List<Bus> busList=busService.getAllBus();
        model.addAttribute("bus",busList);
        return "viewBuses";
    }

    //method to search form bus by busId (optional for convenient)
    @GetMapping("/findBusById")
    public String findBus(){
        return "findBusId";
    }

    @GetMapping("/findBusId")
    public String findBusById(@RequestParam int busId, Model model){
        Optional<Bus> bus=busService.getBusByIdOptional(busId);
        if(bus.isEmpty()){
            model.addAttribute("error","Bus Details Not Found with this Id");
            return "error";
        }else{
            model.addAttribute("bus",bus.get());
            return "viewBuses";
        }
    }

    //Method to show UpdateForm
    @GetMapping("updateByBus")
    public String busUpdatePage(@RequestParam int busId, Model model){
        Optional<Bus> bus=busService.getBusByIdOptional(busId);

        if(bus.isPresent()){
            model.addAttribute("bus",bus.get());
            return "updateBus";
        }else{
            model.addAttribute("error","Bus not found, Unable to update!!!");
            return "error";
        }
    }

    //Method to update bus in Database
    @PutMapping("/saveUpdateBus")
    public String updateByBus(@ModelAttribute Bus bus,Model model){
        try{
            Bus updateBus=busService.updateBus(bus);
            model.addAttribute("bus",updateBus);
            return "viewBuses";
        }catch (RuntimeException e){
            model.addAttribute("error","Bus not found, Unable to update");
            return "error";
        }
    }

    // Method to Delete Bus from List
    @DeleteMapping("/delete/{serialNo}")
    public String deleteBus(@PathVariable int serialNo, Model model){
        try {
            busService.deleteBus(serialNo);
            model.addAttribute("successMessage", "Bus deleted successfully.");
            return "delete";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Something went wrong. " +
                    "Deletion unsuccessful.");
        }
        return "viewBuses"; // Redirect to viewBuses page after deletion
    }
}

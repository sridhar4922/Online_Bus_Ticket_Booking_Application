package com.BusBookingManagement.BusTicketBookingManagement.Controller;



import com.BusBookingManagement.BusTicketBookingManagement.Entity.Booking;
import com.BusBookingManagement.BusTicketBookingManagement.Entity.Bus;
import com.BusBookingManagement.BusTicketBookingManagement.Entity.PassengerDetail;
import com.BusBookingManagement.BusTicketBookingManagement.Entity.Passengers;
import com.BusBookingManagement.BusTicketBookingManagement.Service.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private BusService busService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private GeneratePdfService generatePdfService;

    //method to findBus
    @GetMapping("/passengerHome")
    public String findRespectiveBuses(@RequestParam("from")String from,
                                      @RequestParam("to")String to,
                                      @RequestParam("date")@DateTimeFormat (iso = DateTimeFormat.ISO.DATE)
                                      LocalDate checkingDate, Model model, Principal principal){

        List<Bus> filteredBuses=busService.findBuses(from,to,checkingDate);
        if(filteredBuses.isEmpty()){
            model.addAttribute("noBusesFound",true);
            return "noBusesFoundPage";
        }
            model.addAttribute("buses", filteredBuses);
            CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(principal.getName());
            Passengers passengers = userDetails.getPassengers();
            model.addAttribute("passenger", passengers);
            return "searchedBuses";
    }

    //Method to Book Bus by it BusId
    @GetMapping("/bookBus/{busId}")
    public String bookBus(@PathVariable int busId, Model model) {
        Bus bus = busService.getBusById(busId);
        model.addAttribute("bus",bus);
        model.addAttribute("booking",new Booking());

        Passengers loggedInUser=passengerService.getCurrentLoggedInPassenger();
        model.addAttribute("passengers",loggedInUser);
        return "bookBus";
    }

    // Method to save bus booking
    @PostMapping("/saveBooking")
    public String saveBooking(@ModelAttribute("booking") Booking booking,
                              @RequestParam("busId") int busId,
                              @RequestParam("passengerId") long passengerId,
                              @RequestParam("noOfPassengers") int noOfPassengers,
                              @RequestParam("travelPassengerName") List<String> travelPassengerNames,
                              @RequestParam("travelPassengerGender") List<String> travelPassengerGenders,
                              @RequestParam("travelPassengerAge") List<Integer> travelPassengerAges,
                              @RequestParam("seatPreference") List<String> seatPreferences,
                              @RequestParam("paymentMode") String paymentMode){
        Bus bus = busService.getBusById(busId);
        Passengers passengers = passengerService.getById(passengerId);
        booking.setBusEntity(bus);
        booking.setPassengers(passengers);
        booking.setNoOfPassengers(noOfPassengers);
        booking.setPaymentMode(paymentMode);
        booking.setBookingDate(Date.valueOf(LocalDate.now()));

        // Handle individual passenger details
        List<PassengerDetail> passengerDetails = new ArrayList<>();
        for (int i = 0; i < noOfPassengers; i++) {
            PassengerDetail detail = new PassengerDetail();
            detail.setTravelPassengerName(travelPassengerNames.get(i));
            detail.setTravelPassengerGender(travelPassengerGenders.get(i));
            detail.setTravelPassengerAge(travelPassengerAges.get(i));
            detail.setSeatPreference(seatPreferences.get(i));
            detail.setBooking(booking); // Set the booking reference
            passengerDetails.add(detail);
        }
        booking.setPassengerDetails(passengerDetails);

        // Save booking with passenger details
        bookingService.saveBookingWithPassengerDetails(booking, passengerDetails);
        return "viewBookings";
    }

    //Method to show individual booking ticket by their  passenger Credential
    @GetMapping("/bookings")
    public String getBookingsForLoggedInPassenger(Model model, Principal principal){
        Passengers loggedInPassenger=passengerService.getCurrentLoggedInPassenger();
        List<Booking> bookingList=bookingService.getBookingByPassenger(loggedInPassenger);
        model.addAttribute("booking",bookingList);

        //Function display UserName in FrontPage
        CustomUserDetails userDetails=(CustomUserDetails)userDetailsService.loadUserByUsername(principal.getName());
        Passengers passengers=userDetails.getPassengers();
        model.addAttribute("passenger",passengers);

        return "viewBookings";

    }

    //Method to Generate PDF
    @GetMapping("/generatePdf/{bookingId}")
    public StreamingResponseBody generatePdf(@PathVariable int bookingId, HttpServletResponse response) {
        Booking booking = bookingService.getByBookingId(bookingId);
        Map<String, Object> data = new HashMap<>();
        data.put("booking", booking);

        String pdfFileName = "Booking_" + bookingId + ".pdf";
        generatePdfService.generatePdfFile("booking_template", data, pdfFileName);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=" + pdfFileName);

        try {
            InputStream inputStream = new FileInputStream(pdfFileName);
            return outputStream -> {
                int nRead;
                byte[] dataBuffer = new byte[1024];
                while ((nRead = inputStream.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                    outputStream.write(dataBuffer, 0, nRead);
                }
                inputStream.close();
            };
        } catch (IOException e) {
            throw new RuntimeException("Error while streaming PDF", e);
        }
    }
}



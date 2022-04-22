package com.carrentalproject.controller;

import com.carrentalproject.domain.Car;
import com.carrentalproject.domain.Reservation;
import com.carrentalproject.dto.ReservationDTO;
import com.carrentalproject.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins="*", maxAge = 3600)
@AllArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@RestController
@RequestMapping("/reservations")
public class ReservationController {

  public ReservationService reservationService;


  @PostMapping("/add")
  @PreAuthorize("hasRole('CUSTOMER')or hasRole('ADMIN')")
  public ResponseEntity<Map<String,Boolean>> makeReservation(HttpServletRequest request,
                                                             @RequestParam (value="carId") Car carId,
                                                             @Valid @RequestBody Reservation reservation){
      Long userId=(Long) request.getAttribute("id");
      reservationService.addReservation(reservation,userId,carId);

      Map<String,Boolean> map=new HashMap<>();
      map.put("Reservation added succesfully!",true);
      return new ResponseEntity<>(map, HttpStatus.CREATED);

  }

    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('ADMIN')") //tum reservation lari alan metod
    public ResponseEntity<List<ReservationDTO>> getAllReservations(){
        List<ReservationDTO> reservations = reservationService.fetchAllReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/admin/auth/all")
    @PreAuthorize("hasRole('ADMIN')") // Admin tarafindan kullanicinin reservation larini alan metod
    public ResponseEntity<List<ReservationDTO>> getAllUserReservations(@RequestParam (value = "userId") Long userId){
        List<ReservationDTO> reservations = reservationService.findAllByUserId(userId);
        return new ResponseEntity<>(reservations, HttpStatus.OK); //
    }

    @GetMapping("/{id}/admin")
    @PreAuthorize("hasRole('ADMIN')") //reservation id ile reservation i alan metod
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id){
        ReservationDTO reservation = reservationService.findById(id);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @GetMapping("/{id}/auth")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')") // kullanicinin id ile kendi reservation ina ulastigi metod
    public ResponseEntity<ReservationDTO> getUserReservationById(@PathVariable Long id,
                                                                 HttpServletRequest request){
        Long userId = (Long) request.getAttribute("id");
        ReservationDTO reservation = reservationService.findByIdAndUserId(id, userId);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @GetMapping("/auth/all")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')") //kullanicinin userId ile tum reservation larina ulastigi metod
    public ResponseEntity<List<ReservationDTO>> getUserReservationsById(HttpServletRequest request){
        Long userId = (Long) request.getAttribute("id");
        List<ReservationDTO> reservation = reservationService.findAllByUserId(userId);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @PutMapping("/admin/auth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,Boolean>> updateReservation(@RequestParam(value="carId")Car carId,
                                                                 @RequestParam(value="reservationId") Long reservationId,
                                                                 @Valid @RequestBody Reservation reservation){ //fontend den gelen bilgiler
      reservationService.updateReservation(carId, reservationId, reservation);
        Map<String,Boolean> map=new HashMap<>();
        map.put("success!",true);
        return new ResponseEntity<>(map, HttpStatus.CREATED);

    }
}

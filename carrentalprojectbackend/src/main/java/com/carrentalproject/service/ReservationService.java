package com.carrentalproject.service;

import com.carrentalproject.domain.Car;
import com.carrentalproject.domain.Reservation;
import com.carrentalproject.domain.User;
import com.carrentalproject.domain.enumeration.ReservationStatus;
import com.carrentalproject.dto.ReservationDTO;
import com.carrentalproject.exception.BadRequestException;
import com.carrentalproject.exception.ResourceNotFoundException;
import com.carrentalproject.repository.CarRepository;
import com.carrentalproject.repository.ReservationRepository;
import com.carrentalproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private static final String USER_NOT_FOUND_MSG = "user with id %d not found";
    private static final String CAR_NOT_FOUND_MSG = "car with id %d not found";
    private static final String RESERVATION_NOT_FOUND_MSG = "reservation with id %d not found";


    public List<ReservationDTO> fetchAllReservations(){
        return reservationRepository.findAllReservation();
    }
    public ReservationDTO findById (Long id){
        return reservationRepository.findReservationById(id).orElseThrow(()->
                new ResourceNotFoundException(String.format(RESERVATION_NOT_FOUND_MSG,id)));
    }
    public ReservationDTO findByIdAndUserId(Long id, Long userId){
        return reservationRepository.findReservationByUserId(id,userId).orElseThrow(()->
                new ResourceNotFoundException(String.format(RESERVATION_NOT_FOUND_MSG,id)));
    }
    public List<ReservationDTO> findAllByUserId(Long userId){
        return reservationRepository.findReservationsByUserId(userId);
    }

    public void addReservation(Reservation reservation, Long userId, Car carId)
    throws BadRequestException {
        boolean checkStatus= carAvailability(carId.getId(),reservation.getPickUpTime(),reservation.getDropOffTime());
        User user= userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG, userId)));
        if(!checkStatus)
            reservation.setStatus(ReservationStatus.CREATED);
        else
            throw new BadRequestException("Car is already reserved, please choose another");
        reservation.setCarId(carId);
        reservation.setUserId(user);

        Double totalPrice=totalPrice(reservation.getPickUpTime(),reservation.getDropOffTime(),carId.getId());
        reservation.setTotalPrice(totalPrice);
        reservationRepository.save(reservation);
    }

    public void updateReservation(Car carId,Long id,Reservation reservation)throws BadRequestException{
        boolean checkStatus=carAvailability(carId.getId(),reservation.getPickUpTime(),reservation.getDropOffTime());

        Optional<Reservation> reservationExist=reservationRepository.findById(id);
        //girilen tum update olmasini istedigimiz verileri databaseden reservationExist alanina kaydediyoruz.
        if (reservationExist.isEmpty()){
            throw new ResourceNotFoundException("Error: Reservation does not exist!");
        }
        if(reservation.getPickUpTime().compareTo(reservationExist.get().getPickUpTime())==0 &&
        reservation.getDropOffTime().compareTo(reservationExist.get().getDropOffTime())==0 &&
        carId==reservationExist.get().getCarId())
            System.out.println();
        else if(checkStatus)
            throw new BadRequestException("Car is already reserved! Please choose another");

        Double totalPrice=totalPrice(reservation.getPickUpTime(),reservation.getDropOffTime(),carId.getId());
        reservationExist.get().setTotalPrice(totalPrice);

        reservationExist.get().setCarId(carId);
        reservationExist.get().setPickUpTime(reservation.getPickUpTime());
        reservationExist.get().setDropOffTime(reservation.getDropOffTime());
        reservationExist.get().setPickUpLocation(reservation.getPickUpLocation());
        reservationExist.get().setDropOffLocation(reservation.getDropOffLocation());
        reservationExist.get().setStatus(reservation.getStatus());

        reservationRepository.save(reservationExist.get());
    }

    public Double totalPrice(LocalDateTime pickUpTime, LocalDateTime dropOffTime, Long carId){
        Car car= carRepository.findById(carId).orElseThrow(()->
                new ResourceNotFoundException(String.format(CAR_NOT_FOUND_MSG,carId)));
    Long hours=(new Reservation()).getTotalHours(pickUpTime,dropOffTime);
    return car.getPricePerHour()*hours;
    }

    public boolean carAvailability(Long carId, LocalDateTime pickUpTime,  LocalDateTime dropOffTime){
        List<Reservation> checkStatus=reservationRepository.checkStatus(carId,pickUpTime,dropOffTime,
                ReservationStatus.DONE,ReservationStatus.CANCELED);

                return checkStatus.size()>0;

    }
















}

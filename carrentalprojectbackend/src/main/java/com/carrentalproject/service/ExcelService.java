package com.carrentalproject.service;

import com.carrentalproject.domain.Car;
import com.carrentalproject.domain.Reservation;
import com.carrentalproject.domain.User;
import com.carrentalproject.helper.ExcelHelper;
import com.carrentalproject.repository.CarRepository;
import com.carrentalproject.repository.ReservationRepository;
import com.carrentalproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@AllArgsConstructor
@Service
public class ExcelService {

    UserRepository userRepository;
    CarRepository carRepository;
    ReservationRepository reservationRepository;

    public ByteArrayInputStream loadUser(){
        List<User> users= userRepository.findAll();

        return ExcelHelper.usersExcel(users);

    }

    public ByteArrayInputStream loadCar(){
        List<Car> cars= carRepository.findAll();

        return ExcelHelper.carsExcel(cars);

    }

    public ByteArrayInputStream loadReservation(){
        List<Reservation> reservations=reservationRepository.findAll();

        return ExcelHelper.reservationsExcel(reservations);
    }



}

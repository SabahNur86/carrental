package com.carrentalproject.service;

import com.carrentalproject.domain.Car;
import com.carrentalproject.domain.FileDB;
import com.carrentalproject.exception.BadRequestException;
import com.carrentalproject.exception.ResourceNotFoundException;
import com.carrentalproject.repository.CarRepository;
import com.carrentalproject.repository.FileDBRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    //private final ReservationRepository reservationRepository;

    private final FileDBRepository fileDBRepository;

    private final static String IMAGE_NOT_FOUND_MSG = "image with id %s not found";

    public void add(Car car, String imageId) throws BadRequestException {
        FileDB fileDB = fileDBRepository.findById(imageId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(IMAGE_NOT_FOUND_MSG, imageId)));

        Set<FileDB> fileDBs = new HashSet<>();
        fileDBs.add(fileDB);

        car.setImage(fileDBs);
        car.setBuiltIn(false);
        carRepository.save(car);
    }

    public void updateCar(Long id,Car car,String imageId) throws BadRequestException{
        car.setId(id);//var olan kalsin degismesin diye
        FileDB fileDB=fileDBRepository.findById(imageId).get();
        Car car1= carRepository.getById(id);
        if (car1.getBuiltIn()){
            throw new BadRequestException("You do not permission to update car");
        }
        car.setBuiltIn(false);
        Set<FileDB> fileDBS=new HashSet<>();
        fileDBS.add(fileDB);
        car.setImage(fileDBS);
        carRepository.save(car);
    }

}


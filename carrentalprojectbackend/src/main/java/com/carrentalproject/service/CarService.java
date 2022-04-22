package com.carrentalproject.service;

import com.carrentalproject.domain.Car;
import com.carrentalproject.domain.FileDB;
import com.carrentalproject.domain.User;
import com.carrentalproject.dto.CarDTO;
import com.carrentalproject.exception.BadRequestException;
import com.carrentalproject.exception.ResourceNotFoundException;
import com.carrentalproject.repository.CarRepository;
import com.carrentalproject.repository.FileDBRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    //private final ReservationRepository reservationRepository;

    private final FileDBRepository fileDBRepository;

    private final static String IMAGE_NOT_FOUND_MSG = "image with id %s not found";
    private final static String CAR_NOT_FOUND_MSG = "car with id %d not found";

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

    public List<CarDTO> fetchAllCars(){
      return carRepository.findAllCar(); //repositorydeki sorgusunu yazdigimiz metodu cagirdik
    }

    public CarDTO findById(Long id) throws ResourceNotFoundException{
      return carRepository.findCarByIdx(id).orElseThrow(()->
              new ResourceNotFoundException(String.format(CAR_NOT_FOUND_MSG,id)));

    }

    public void removeById(Long id)throws ResourceNotFoundException{
        Car car =carRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(String.format(CAR_NOT_FOUND_MSG, id)));
        if(car.getBuiltIn()){
            throw new BadRequestException("you dont have permission to delete user");
        }
        carRepository.deleteById(id); //jpa in hazir silme metodu ile araba kaydimizi silme metodumuzu
                                      // hazirladik controller da bunu cagiricaz
    }

}


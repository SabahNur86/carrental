package com.carrentalproject.dto;

import com.carrentalproject.domain.Car;
import com.carrentalproject.domain.FileDB;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CarDTO {

    private Long id;

    private String model;

    private Integer doors;

    private Integer seats;

    private Integer luggage;

    private String transmission;

    private Boolean airConditioning;

    private Integer age;

    private Double pricePerHour;

    private String fuelType;

    private Boolean builtIn;

    private Set<String> image;

    public Set<String> getImageId(Set<FileDB> image){
        Set<String> img=new HashSet<>();
        FileDB [] fileDB=image.toArray(new FileDB[image.size()]);
        for(int i=0; i<image.size(); i++){
            img.add(fileDB[i].getId());
        }return img;
    }

    public CarDTO(Car car) {
        this.id = car.getId();
        this.model = car.getModel();
        this.doors = car.getDoors();
        this.seats = car.getSeats();
        this.luggage = car.getLuggage();
        this.transmission = car.getTransmission();
        this.airConditioning = car.getAirConditioning();
        this.age = car.getAge();
        this.pricePerHour = car.getPricePerHour();
        this.fuelType = car.getFuelType();
        this.image = getImageId(car.getImage());
        this.builtIn = car.getBuiltIn();
    }
}

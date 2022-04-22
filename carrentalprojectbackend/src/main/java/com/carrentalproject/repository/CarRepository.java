package com.carrentalproject.repository;

import com.carrentalproject.domain.Car;
import com.carrentalproject.dto.CarDTO;
import com.carrentalproject.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT new com.carrentalproject.dto.CarDTO(c) FROM Car c" ) //tum car lari aldigimiz sorguyu
    List<CarDTO> findAllCar();                                          // kendimiz yazdik

    //id ile car a ulasmak icin sorgu;
    @Query("SELECT new com.carrentalproject.dto.CarDTO(c) FROM Car c WHERE c.id=?1" )
    Optional<CarDTO> findCarByIdx(Long id) throws ResourceNotFoundException;

}

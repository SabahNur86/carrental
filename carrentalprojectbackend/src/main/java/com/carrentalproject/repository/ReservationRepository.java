package com.carrentalproject.repository;

import com.carrentalproject.domain.Reservation;
import com.carrentalproject.domain.enumeration.ReservationStatus;
import com.carrentalproject.dto.ReservationDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ReservationRepository extends JpaRepository<Reservation,Long> {


    @Query("SELECT new com.carrentalproject.dto.ReservationDTO(r) FROM Reservation r")
    List<ReservationDTO> findAllReservation(); //tum reservation bilgilerini aliyoruz


    @Query("SELECT new com.carrentalproject.dto.ReservationDTO(r) FROM Reservation r WHERE r.id = ?1")
    Optional<ReservationDTO> findReservationById(Long id); //reservation id ile sorgulama


    @Query("SELECT new com.carrentalproject.dto.ReservationDTO(r) FROM Reservation r WHERE r.id = ?1 and r.userId.id = ?2")
    Optional<ReservationDTO> findReservationByUserId(Long id, Long userId); //userId ile reservation sorgulama


    @Query("SELECT new com.carrentalproject.dto.ReservationDTO(r) FROM Reservation r WHERE r.userId.id = ?1")
    List<ReservationDTO> findReservationsByUserId(Long userId); //admin in userId ile reservation larini sorgulama


    @Query("SELECT r FROM Reservation r " +
    "LEFT JOIN fetch r.carId cd " +
    "LEFT JOIN fetch cd.image img " +
    "LEFT JOIN fetch r.userId uid " +
    "WHERE (cd.id =?1 and r.status <> ?4 and r.status <> ?5 and ?2 BETWEEN r.pickUpTime and r.dropOffTime)or" +
            "(cd.id =?1 and r.status <> ?4 and r.status <> ?5 and ?3 BETWEEN r.pickUpTime and r.dropOffTime)")
    List<Reservation> checkStatus(Long carId, LocalDateTime pickUpTime, LocalDateTime dropOffTime, ReservationStatus done,ReservationStatus canceled);


}

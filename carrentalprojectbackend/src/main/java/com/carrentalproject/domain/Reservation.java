package com.carrentalproject.domain;

import com.carrentalproject.domain.enumeration.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name="reservations")
@Getter
@Setter
@NoArgsConstructor
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST)//rezervation silinince car silinmesin diye(persist)
    @JoinColumn(name="car_id",referencedColumnName = "id")
    private Car carId;

    @OneToOne(cascade = CascadeType.PERSIST)//rezervation silinince car silinmesin diye(persist)
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private User userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="MM/dd/yyyy HH:mm:ss", timezone = "Turkey")
    @NotNull(message = "Please enter the pick up time of the reservation")
    @Column(nullable = false)
    private LocalDateTime pickUpTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="MM/dd/yyyy HH:mm:ss", timezone = "Turkey")
    @NotNull(message = "Please enter the drop up time of the reservation")
    @Column(nullable = false)
    private LocalDateTime dropOffTime;

    @Column(length = 50, nullable = false)
    @NotNull(message = "Please enter the pick up location of the reservation")
    private String pickUpLocation;

    @Column(length = 50, nullable = false)
    @NotNull(message = "Please enter the drop off location of the reservation")
    private String dropOffLocation;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private ReservationStatus status;

    @Column(nullable = false)
    private Double totalPrice;

    public Long getTotalHours(LocalDateTime pickUpTime, LocalDateTime dropOffTime) {

       // Long seconds = ChronoUnit.SECONDS.between(pickUpTime, dropOffTime);
     //   Long minutes = ChronoUnit.MINUTES.between(pickUpTime, dropOffTime);
        Long hours = ChronoUnit.HOURS.between(pickUpTime, dropOffTime);
     //   Long days = ChronoUnit.DAYS.between(pickUpTime, dropOffTime);

        return hours;
    }



}

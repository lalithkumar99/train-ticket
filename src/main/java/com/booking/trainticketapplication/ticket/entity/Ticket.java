package com.booking.trainticketapplication.ticket.entity;

import com.booking.trainticketapplication.passenger.entity.Passenger;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fromPlace;
    private String toPlace;
    @ManyToOne
    @JoinColumn(name = "passengerId")
    private Passenger passenger;
    private Long price;
    private String section;
    private String seatNo;
    @PostPersist
    public void postfixKey(){
        if (StringUtils.isEmpty(seatNo)) {
            seatNo = this.section + this.id;
        }
    }
}

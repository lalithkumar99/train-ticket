package com.booking.trainticketapplication.ticket.dto;

import com.booking.trainticketapplication.passenger.dto.PassengerDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PassengerAllocatedDto {
    private PassengerDto passenger;
    private String section;
    private String seatNo;
}

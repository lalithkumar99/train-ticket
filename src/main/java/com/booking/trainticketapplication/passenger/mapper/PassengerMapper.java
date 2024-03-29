package com.booking.trainticketapplication.passenger.mapper;

import com.booking.trainticketapplication.passenger.dto.PassengerDto;
import com.booking.trainticketapplication.passenger.entity.Passenger;
import org.springframework.stereotype.Component;

@Component
public class PassengerMapper {

    public PassengerDto domainToDto(Passenger passenger) {
        PassengerDto passengerDto = new PassengerDto();
            passengerDto.setId(passenger.getId());

        passengerDto.setEmail(passenger.getEmail());
        passengerDto.setLastName(passenger.getLastName());
        passengerDto.setFirstName(passenger.getFirstName());

        return passengerDto;
    }

    public Passenger dtoToDomain(PassengerDto passengerDto) {
        Passenger passenger = new Passenger();
            passenger.setId(passengerDto.getId());

        passenger.setEmail(passengerDto.getEmail());
        passenger.setLastName(passengerDto.getLastName());
        passenger.setFirstName(passengerDto.getFirstName());

        return passenger;
    }
}

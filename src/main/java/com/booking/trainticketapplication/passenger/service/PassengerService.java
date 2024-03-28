package com.booking.trainticketapplication.passenger.service;

import com.booking.trainticketapplication.passenger.dto.PassengerDto;
import com.booking.trainticketapplication.passenger.entity.Passenger;
import com.booking.trainticketapplication.passenger.mapper.PassengerMapper;
import com.booking.trainticketapplication.passenger.repository.PassengerRepository;
import com.booking.trainticketapplication.passenger.specification.PassengerSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class PassengerService {
    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    @Autowired
    public PassengerService(PassengerRepository passengerRepository, PassengerMapper passengerMapper) {
        this.passengerRepository = passengerRepository;
        this.passengerMapper = passengerMapper;
    }

    public Passenger findByPassengerEmail(String email) {
        email = email.toLowerCase();
        Optional<Passenger> optionalUser = passengerRepository.findOne(PassengerSpecification.byEmail(email));
        return optionalUser.orElse(null);
    }

    public PassengerDto createPassenger(PassengerDto passengerDto){
        Passenger savedPassenger = findByPassengerEmail(passengerDto.getEmail());
        if(Objects.nonNull(savedPassenger)) {
            return passengerMapper.domainToDto(savedPassenger);
        }
        else {
            passengerDto.setEmail(passengerDto.getEmail().toLowerCase());
            Passenger createdPassenger = passengerRepository.save(passengerMapper.dtoToDomain(passengerDto));
            return passengerMapper.domainToDto(createdPassenger);
        }
    }
}

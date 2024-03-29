package com.booking.trainticketapplication.ticket.service;

import com.booking.trainticketapplication.passenger.service.PassengerService;
import com.booking.trainticketapplication.ticket.dto.TicketDto;
import com.booking.trainticketapplication.ticket.dto.PassengerAllocatedDto;
import com.booking.trainticketapplication.passenger.dto.PassengerDto;
import com.booking.trainticketapplication.passenger.entity.Passenger;
import com.booking.trainticketapplication.ticket.entity.Ticket;
import com.booking.trainticketapplication.ticket.mapper.TicketMapper;
import com.booking.trainticketapplication.passenger.mapper.PassengerMapper;
import com.booking.trainticketapplication.ticket.repository.TicketRepository;
import com.booking.trainticketapplication.passenger.repository.PassengerRepository;
import com.booking.trainticketapplication.ticket.specification.TicketSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final PassengerService passengerService;
    private final PassengerMapper passengerMapper;
    private final PassengerRepository passengerRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository, TicketMapper ticketMapper, PassengerService passengerService, PassengerMapper passengerMapper, PassengerRepository passengerRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
        this.passengerService = passengerService;
        this.passengerMapper = passengerMapper;
        this.passengerRepository = passengerRepository;
    }

    public TicketDto purchaseTicket(TicketDto ticketDto) {
        try {
            checkAvailableSeat(ticketDto.getSection());
            PassengerDto passengerDto = passengerService.createPassenger(ticketDto.getPassenger());
            ticketDto.setPassenger(passengerDto);
            Ticket ticket = ticketRepository.save(ticketMapper.dtoToDomain(ticketDto));
            return ticketMapper.dtoToDomain(ticket);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void checkAvailableSeat(String section) {
        long seatLimitPerSection = 50L;
        Specification<Ticket> specification = TicketSpecification.bySection(section);
        long currentSeatBooked = ticketRepository.findAll(specification).size();
        if (currentSeatBooked + 1L >= seatLimitPerSection) {
            throw new InvalidParameterException("Asked Section is fully booked try different one");
        }
    }

    public TicketDto ticketReceiptOfPassenger(String email) {
        try {
            Passenger passenger = passengerService.findByPassengerEmail(email);
            if (Objects.isNull(passenger)) {
                return null;
            }
            Specification<Ticket> ticketSpecification = TicketSpecification.byPassengerId(passenger.getId());
            Optional<Ticket> optionalTicket = ticketRepository.findOne(ticketSpecification);
            return optionalTicket.map(ticketMapper::dtoToDomain).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<PassengerAllocatedDto> getPassengerBySection(String section) {
        List<PassengerAllocatedDto> passengerAllocatedDtoList = new ArrayList<>();
        try {
            List<Ticket> tickets = ticketRepository.findAll(TicketSpecification.bySection(section));
            if (tickets.size() > 0) {
                tickets.forEach(ticket -> {
                    PassengerAllocatedDto passengerAllocatedDto = new PassengerAllocatedDto();
                    passengerAllocatedDto.setSection(section);
                    passengerAllocatedDto.setPassenger(passengerMapper.domainToDto(ticket.getPassenger()));
                    passengerAllocatedDto.setSeatNo(ticket.getSeatNo());
                    passengerAllocatedDtoList.add(passengerAllocatedDto);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return passengerAllocatedDtoList;
    }

    public Boolean removePassenger(String passengerEmail) {
        try {
            Passenger passenger = passengerService.findByPassengerEmail(passengerEmail);
            if (Objects.isNull(passenger)) {
                return false;
            }
            Specification<Ticket> ticketSpecification = TicketSpecification.byPassengerId(passenger.getId());
            Optional<Ticket> optionalTicket = ticketRepository.findOne(ticketSpecification);
            optionalTicket.ifPresent(ticketRepository::delete);
            passengerRepository.delete(passenger);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public PassengerAllocatedDto modifySeat(String passengerEmail, String section) {
        try {
            Passenger passenger = passengerService.findByPassengerEmail(passengerEmail);
            if (Objects.isNull(passenger)) {
                return null;
            }
            checkAvailableSeat(section);
            Optional<Ticket> optionalTicket = ticketRepository.findOne(TicketSpecification.byPassengerId(passenger.getId()));
            if (optionalTicket.isPresent()) {
                Ticket ticket = optionalTicket.get();
                ticket.setSection(section);
                ticketRepository.saveAndFlush(ticket);
                PassengerAllocatedDto passengerAllocatedDto = new PassengerAllocatedDto();
                passengerAllocatedDto.setSeatNo(ticket.getSeatNo());
                passengerAllocatedDto.setSection(ticket.getSection());
                passengerAllocatedDto.setPassenger(passengerMapper.domainToDto(ticket.getPassenger()));
                return passengerAllocatedDto;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

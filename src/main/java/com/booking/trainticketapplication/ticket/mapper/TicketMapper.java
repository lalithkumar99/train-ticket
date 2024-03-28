package com.booking.trainticketapplication.ticket.mapper;

import com.booking.trainticketapplication.passenger.mapper.PassengerMapper;
import com.booking.trainticketapplication.ticket.dto.TicketDto;
import com.booking.trainticketapplication.ticket.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {
    private final PassengerMapper passengerMapper;

    @Autowired
    public TicketMapper(PassengerMapper passengerMapper) {
        this.passengerMapper = passengerMapper;
    }

    public TicketDto dtoToDomain(Ticket ticket) {
        TicketDto ticketDto = new TicketDto();
            ticketDto.setId(ticket.getId());

        ticketDto.setFromPlace(ticket.getFromPlace());
        ticketDto.setToPlace(ticket.getToPlace());
        ticketDto.setPrice(ticket.getPrice());
        ticketDto.setSection(ticket.getSection());
        ticketDto.setPassenger(passengerMapper.domainToDto(ticket.getPassenger()));
        ticketDto.setSeatNo(ticket.getSeatNo());

        return ticketDto;
    }

    public Ticket dtoToDomain(TicketDto ticketDto) {
        Ticket ticket = new Ticket();
            ticket.setId(ticketDto.getId());

        ticket.setFromPlace(ticketDto.getFromPlace());
        ticket.setToPlace(ticketDto.getToPlace());
        ticket.setPrice(ticketDto.getPrice());
        ticket.setSection(ticketDto.getSection());
        ticket.setPassenger(passengerMapper.dtoToDomain(ticketDto.getPassenger()));
        ticket.setSeatNo(ticketDto.getSeatNo());

        return ticket;
    }
}

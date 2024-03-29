package com.booking.trainticketapplication;

import com.booking.trainticketapplication.ticket.controller.TicketController;
import com.booking.trainticketapplication.ticket.dto.PassengerAllocatedDto;
import com.booking.trainticketapplication.ticket.dto.TicketDto;
import com.booking.trainticketapplication.ticket.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class TicketControllerTest {

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void purchaseTicket_Success() {
        TicketDto ticketDto = new TicketDto();
        when(ticketService.purchaseTicket(any(TicketDto.class))).thenReturn(ticketDto);

        ResponseEntity<TicketDto> responseEntity = ticketController.purchaseTicket(new TicketDto());

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(ticketDto, responseEntity.getBody());
    }

    @Test
    void purchaseTicket_Failure() {
        when(ticketService.purchaseTicket(any(TicketDto.class))).thenReturn(null);

        ResponseEntity<TicketDto> responseEntity = ticketController.purchaseTicket(new TicketDto());

        assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
    }

    @Test
    void getReceipt_NotFound() {
        when(ticketService.ticketReceiptOfPassenger(anyString())).thenReturn(null);

        ResponseEntity<TicketDto> responseEntity = ticketController.getReceipt("test@example.com");

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void getReceipt_Found() {
        TicketDto ticketDto = new TicketDto();
        when(ticketService.ticketReceiptOfPassenger(anyString())).thenReturn(ticketDto);

        ResponseEntity<TicketDto> responseEntity = ticketController.getReceipt("test@example.com");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(ticketDto, responseEntity.getBody());
    }

    @Test
    void getPassengersBySection_NoContent() {
        when(ticketService.getPassengerBySection(anyString())).thenReturn(Collections.emptyList());

        ResponseEntity<List<PassengerAllocatedDto>> responseEntity = ticketController.getPassengersBySection("A");

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void getPassengersBySection_Found() {
        List<PassengerAllocatedDto> passengerAllocatedDtos = Collections.singletonList(new PassengerAllocatedDto());
        when(ticketService.getPassengerBySection(anyString())).thenReturn(passengerAllocatedDtos);

        ResponseEntity<List<PassengerAllocatedDto>> responseEntity = ticketController.getPassengersBySection("A");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(passengerAllocatedDtos, responseEntity.getBody());
    }

    @Test
    void removePassenger_NotFound() {
        when(ticketService.removePassenger(anyString())).thenReturn(false);

        ResponseEntity<Void> responseEntity = ticketController.removePassenger("test@example.com");

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void removePassenger_Found() {
        when(ticketService.removePassenger(anyString())).thenReturn(true);

        ResponseEntity<Void> responseEntity = ticketController.removePassenger("test@example.com");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void modifyUserSeat_NotFound() {
        when(ticketService.modifySeat(anyString(), anyString())).thenReturn(null);

        ResponseEntity<PassengerAllocatedDto> responseEntity = ticketController.modifyUserSeat("test@example.com", "A");

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void modifyUserSeat_Found() {
        PassengerAllocatedDto passengerAllocatedDto = new PassengerAllocatedDto();
        when(ticketService.modifySeat(anyString(), anyString())).thenReturn(passengerAllocatedDto);

        ResponseEntity<PassengerAllocatedDto> responseEntity = ticketController.modifyUserSeat("test@example.com", "A");

        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
        assertEquals(passengerAllocatedDto, responseEntity.getBody());
    }
}



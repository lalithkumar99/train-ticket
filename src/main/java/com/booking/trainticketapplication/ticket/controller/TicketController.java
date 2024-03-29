package com.booking.trainticketapplication.ticket.controller;

import com.booking.trainticketapplication.ticket.dto.TicketDto;
import com.booking.trainticketapplication.ticket.dto.PassengerAllocatedDto;
import com.booking.trainticketapplication.ticket.service.TicketService;
import com.booking.trainticketapplication.validation.ValidSection;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/train-ticket")
public class TicketController {
    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping(path = "purchase-ticket")
    public ResponseEntity<TicketDto> purchaseTicket(@RequestBody @Valid TicketDto ticketRequest) {
        TicketDto ticketDto = ticketService.purchaseTicket(ticketRequest);
        if (Objects.isNull(ticketDto)) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(ticketDto, HttpStatus.CREATED);
    }

    /**
     * Generally in enterprise application we don't expose id's of passenger,
     * that's why we have handled all our APIs using passenger email than id,
     * this can be also done using gid's for passengers or any entity class
     */
    @GetMapping(path = "ticket-receipt/{passengerEmail}")
    public ResponseEntity<TicketDto> getReceipt(@PathVariable @Email String passengerEmail) {
        TicketDto ticketDto = ticketService.ticketReceiptOfPassenger(passengerEmail);
        if (Objects.isNull(ticketDto)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ticketDto, HttpStatus.OK);
    }

    @GetMapping(path = "passengers/{section}")
    public ResponseEntity<List<PassengerAllocatedDto>> getPassengersBySection(@PathVariable @ValidSection String section) {
        List<PassengerAllocatedDto> passengerAllocatedSeat = ticketService.getPassengerBySection(section);
        if (passengerAllocatedSeat.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(passengerAllocatedSeat, HttpStatus.OK);
    }

    @DeleteMapping(path = "remove/{passengerEmail}")
    public ResponseEntity<Void> removePassenger(@PathVariable @Email String passengerEmail) {
        boolean removed = ticketService.removePassenger(passengerEmail);
        if (!removed) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(path = "modifySeat/{passengerEmail}")
    public ResponseEntity<PassengerAllocatedDto> modifyUserSeat(@PathVariable @Email String passengerEmail, @RequestParam @ValidSection String newSection) {
        PassengerAllocatedDto passengerAllocatedDto = ticketService.modifySeat(passengerEmail, newSection);
        if (Objects.isNull(passengerAllocatedDto)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(passengerAllocatedDto, HttpStatus.ACCEPTED);
    }
}

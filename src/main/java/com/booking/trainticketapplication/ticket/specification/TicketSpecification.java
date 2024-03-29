package com.booking.trainticketapplication.ticket.specification;

import com.booking.trainticketapplication.ticket.entity.Ticket;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TicketSpecification {
    public static Specification<Ticket> bySection(String section) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("section"), section);
    }

    public static Specification<Ticket> byPassengerId(Long passengerId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("passenger").get("id"), passengerId);
    }
}

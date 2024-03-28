package com.booking.trainticketapplication.passenger.specification;

import com.booking.trainticketapplication.passenger.entity.Passenger;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PassengerSpecification {
    public static Specification<Passenger> byEmail(String email) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("email"), email);
    }
}

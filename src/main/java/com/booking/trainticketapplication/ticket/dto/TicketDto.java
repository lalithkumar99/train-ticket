package com.booking.trainticketapplication.ticket.dto;

import com.booking.trainticketapplication.passenger.dto.PassengerDto;
import com.booking.trainticketapplication.validation.ValidSection;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.PostPersist;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Setter
@Getter
public class TicketDto {
    @JsonIgnore
    private Long id;
    @NotBlank
    private String fromPlace;
    @NotBlank
    private String toPlace;
    @NotNull
    @Valid
    private PassengerDto passenger;
    @NotNull
    @Min(0L)
    private Long price;
    @NotBlank
    @ValidSection
    private String section;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String seatNo;
}

package com.agendexa.dto;

import java.time.LocalDateTime;

public record AppointmentRequestDTO(Long specialistId, LocalDateTime startTime){
}

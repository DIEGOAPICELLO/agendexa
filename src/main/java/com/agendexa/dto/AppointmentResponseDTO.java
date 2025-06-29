package com.agendexa.dto;

import com.agendexa.enums.AppointmentStatus;

import java.time.LocalDateTime;

public record AppointmentResponseDTO(Long id, LocalDateTime startTime, LocalDateTime endTime, UserInfo specialist, UserInfo patient, AppointmentStatus status) {
}

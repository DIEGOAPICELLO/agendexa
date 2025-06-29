package com.agendexa.repository;

import com.agendexa.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findBySpecialistId(Long specialistId);

    // Query para encontrar agendamentos que se sobrepõem a um novo intervalo de tempo
    @Query("SELECT a FROM Appointment a WHERE a.specialist.id = :specialistId " +
            "AND a.status = 'SCHEDULED' " +
            "AND ((a.startTime < :endTime) AND (a.endTime > :startTime))")
    List<Appointment> findOverlappingAppointments(
            @Param("specialistId") Long specialistId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
}

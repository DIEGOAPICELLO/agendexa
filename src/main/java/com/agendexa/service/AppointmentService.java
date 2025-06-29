package com.agendexa.service;

import com.agendexa.dto.AppointmentRequestDTO;
import com.agendexa.dto.AppointmentResponseDTO;
import com.agendexa.dto.UserInfo;
import com.agendexa.entity.Appointment;
import com.agendexa.entity.User;
import com.agendexa.enums.AppointmentStatus;
import com.agendexa.enums.UserRole;
import com.agendexa.repository.AppointmentRepository;
import com.agendexa.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public AppointmentResponseDTO createAppointment (AppointmentRequestDTO requestDTO, User patient) {
        //1 - validar se o especialista existe e se ele tem realmente perfil de SPECIALIST
        User specialist = userRepository.findById(requestDTO.specialistId())
                .filter(user -> user.getRole() == UserRole.SPECIALIST)
                .orElseThrow(() -> new EntityNotFoundException("Especialista não encontrado"));

        //2 - definir a duracao do agendamento (ex: 1h)
        LocalDateTime endTime = requestDTO.startTime().plusHours(1);

        //3 - regra de negocio: verificar se o especialista ja tem um agendamento nesse horario
        if (hasOverlappingAppointment(specialist.getId(), requestDTO.startTime(), endTime)){
            throw new IllegalStateException("O especialista ja poussi agendamento nesse horario");
        }

        //4 - Criar e salvar agendamento
        Appointment newAppointment = new Appointment();
        newAppointment.setSpecialist(specialist);
        newAppointment.setPatient(patient);
        newAppointment.setStartTime(requestDTO.startTime());
        newAppointment.setEndTime(endTime);
        newAppointment.setStatus(AppointmentStatus.SCHEDULED);

        Appointment saveAppointment = appointmentRepository.save(newAppointment);

        //5 - Mapear a entidade para DTO de resposta
        return toResponseDTO(saveAppointment);
    }

    public List<AppointmentResponseDTO> findAppointmentsForUser(User user) {
        List<Appointment> appointmentList;
        if (user.getRole() == UserRole.PATIENT) {
            appointmentList = appointmentRepository.findByPatientId(user.getId());
        } else if (user.getRole() == UserRole.SPECIALIST) {
            appointmentList = appointmentRepository.findBySpecialistId(user.getId());
        } else {
            appointmentList = appointmentRepository.findAll();
        }
        return appointmentList.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private boolean hasOverlappingAppointment(Long specialistId, LocalDateTime start, LocalDateTime end) {
        List<Appointment> existingAppointments = appointmentRepository.findOverlappingAppointments(specialistId, start, end);
        return !existingAppointments.isEmpty();
    }

    private AppointmentResponseDTO toResponseDTO(Appointment appointment) {
        var specialistInfo = new UserInfo(appointment.getSpecialist().getId(), appointment.getSpecialist().getName());
        var patientInfo = new UserInfo(appointment.getPatient().getId(), appointment.getPatient().getName());

        return new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                specialistInfo,
                patientInfo,
                appointment.getStatus()
        );
    }

}

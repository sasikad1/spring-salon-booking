package com.sasika.salon.booking.service;

import com.sasika.salon.booking.entity.Appointment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AppointmentService {
    List<Appointment> getAllAppointments();
    Appointment getAppointmentById(Long id);
    Appointment createAppointment(Appointment appointment);
    Appointment updateAppointmentById(Long id, Appointment appointment);
    String deleteAppointmentById(Long id);
}

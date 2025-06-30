package com.sasika.salon.booking.service.impl;

import com.sasika.salon.booking.entity.Appointment;
import com.sasika.salon.booking.repository.AppointmentRepository;
import com.sasika.salon.booking.service.AppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);

    private final AppointmentRepository appointmentRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<Appointment> getAllAppointments() {
        logger.info("Fetching all appointments");
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        logger.info("Fetching appointment with ID: {}", id);
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + id));
    }

    @Override
    public Appointment createAppointment(Appointment appointment) {
        logger.info("Creating new appointment for customer ID: {}", appointment.getCustomer().getId());
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment updateAppointmentById(Long id, Appointment updatedAppointment) {
        logger.info("Updating appointment with ID: {}", id);
        return appointmentRepository.findById(id)
                .map(existing -> {
                    existing.setCustomer(updatedAppointment.getCustomer());
                    existing.setStaff(updatedAppointment.getStaff());
                    existing.setBranch(updatedAppointment.getBranch());
                    existing.setAppointmentDate(updatedAppointment.getAppointmentDate());
                    existing.setStartTime(updatedAppointment.getStartTime());
                    existing.setEndTime(updatedAppointment.getEndTime());
                    existing.setStatus(updatedAppointment.getStatus());
                    existing.setSalonServices(updatedAppointment.getSalonServices());
                    return appointmentRepository.save(existing);
                })
                .orElseThrow(() -> {
                    logger.warn("Appointment not found with ID: {}", id);
                    return new RuntimeException("Appointment not found with ID: " + id);
                });
    }

    @Override
    public String deleteAppointmentById(Long id) {
        logger.info("Deleting appointment with ID: {}", id);
        if (!appointmentRepository.existsById(id)) {
            return "Appointment with ID: " + id + " not found";
        }
        appointmentRepository.deleteById(id);
        return "Appointment with ID: " + id + " deleted successfully";
    }
}

package com.sasika.salon.booking.service.impl;

import com.sasika.salon.booking.entity.Notification;
import com.sasika.salon.booking.repository.NotificationRepository;
import com.sasika.salon.booking.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> getAllNotifications() {
        logger.info("Fetching all notifications");
        return notificationRepository.findAll();
    }

    @Override
    public Notification getNotificationById(Long id) {
        logger.info("Fetching notification with ID: {}", id);
        return notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with ID: " + id));
    }

    @Override
    public Notification createNotification(Notification notification) {
        logger.info("Creating new notification for customer ID: {}", notification.getCustomer().getId());
        return notificationRepository.save(notification);
    }

    @Override
    public Notification updateNotification(Notification notification) {
        logger.info("Updating notification with ID: {}", notification.getId());
        Notification existing = notificationRepository.findById(notification.getId())
                .orElseThrow(() -> new RuntimeException("Notification not found with ID: " + notification.getId()));

        existing.setCustomer(notification.getCustomer());
        existing.setAppointment(notification.getAppointment());
        existing.setType(notification.getType());
        existing.setSentAt(notification.getSentAt());

        return notificationRepository.save(existing);
    }

    @Override
    public String deleteNotification(Long id) {
        logger.info("Deleting notification with ID: {}", id);
        if (!notificationRepository.existsById(id)) {
            return "Notification with ID: " + id + " not found";
        }
        notificationRepository.deleteById(id);
        return "Notification with ID: " + id + " deleted successfully";
    }
}

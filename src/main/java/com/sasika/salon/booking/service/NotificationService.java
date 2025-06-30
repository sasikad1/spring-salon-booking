package com.sasika.salon.booking.service;

import com.sasika.salon.booking.entity.Notification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {
    List<Notification> getAllNotifications();
    Notification getNotificationById(Long id);
    Notification createNotification(Notification notification);
    Notification updateNotification(Notification notification);
    String deleteNotification(Long id);
}

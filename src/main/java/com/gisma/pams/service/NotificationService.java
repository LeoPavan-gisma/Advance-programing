package com.gisma.pams.service;

import com.gisma.pams.exception.PatientNotFoundException;
import com.gisma.pams.model.Notification;
import com.gisma.pams.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository,
                               PatientService patientService,
                               DoctorService doctorService) {
        this.notificationRepository = notificationRepository;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    public Notification sendNotification(Notification notification) {
        validateNotification(notification);
        return notificationRepository.save(notification);
    }

    public Notification getNotificationById(Long notificationId) {
        return notificationRepository.findById(notificationId)
                .orElseThrow(() -> new PatientNotFoundException("Notification not found with ID: " + notificationId));
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Notification updateNotification(Long notificationId, Notification notificationDetails) {
        Notification existingNotification = getNotificationById(notificationId);

        if (notificationDetails.getTitle() != null && !notificationDetails.getTitle().isBlank()) {
            existingNotification.setTitle(notificationDetails.getTitle());
        }
        if (notificationDetails.getMessage() != null) {
            existingNotification.setMessage(notificationDetails.getMessage());
        }
        if (notificationDetails.getIsRead() != null) {
            existingNotification.setIsRead(notificationDetails.getIsRead());
        }
        if (notificationDetails.getStatus() != null) {
            existingNotification.setStatus(notificationDetails.getStatus());
        }

        return notificationRepository.save(existingNotification);
    }

    public void deleteNotification(Long notificationId) {
        Notification notification = getNotificationById(notificationId);
        notificationRepository.delete(notification);
    }

    public List<Notification> getPatientNotifications(Long patientId) {
        patientService.getPatientById(patientId);
        return notificationRepository.findByPatientPatientId(patientId);
    }

    public List<Notification> getDoctorNotifications(Long doctorId) {
        doctorService.getDoctorById(doctorId);
        return notificationRepository.findByDoctorDoctorId(doctorId);
    }

    public List<Notification> getUnreadNotifications(Long patientId) {
        patientService.getPatientById(patientId);
        return notificationRepository.getUnreadPatientNotifications(patientId);
    }

    public Long getUnreadNotificationCount(Long patientId) {
        patientService.getPatientById(patientId);
        return notificationRepository.getUnreadNotificationCount(patientId);
    }

    public Notification markAsRead(Long notificationId) {
        Notification notification = getNotificationById(notificationId);
        notification.setIsRead(true);
        return notificationRepository.save(notification);
    }

    public List<Notification> markAllAsRead(Long patientId) {
        List<Notification> unreadNotifications = getUnreadNotifications(patientId);
        unreadNotifications.forEach(n -> n.setIsRead(true));
        return notificationRepository.saveAll(unreadNotifications);
    }

    // Method aliases to match controller expectations
    public List<Notification> getUnreadPatientNotifications(Long patientId) {
        return getUnreadNotifications(patientId);
    }

    private void validateNotification(Notification notification) {
        if (notification.getTitle() == null || notification.getTitle().isBlank()) {
            throw new IllegalArgumentException("Notification title is required");
        }
        if (notification.getNotificationType() == null || notification.getNotificationType().isBlank()) {
            throw new IllegalArgumentException("Notification type is required");
        }
    }

    public long getTotalNotificationsCount() {
        return notificationRepository.count();
    }

    public long getUnreadNotificationsCount() {
        return notificationRepository.findByIsReadFalse().size();
    }
}

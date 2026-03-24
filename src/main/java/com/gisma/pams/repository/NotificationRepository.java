package com.gisma.pams.repository;

import com.gisma.pams.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByPatientPatientId(Long patientId);
    List<Notification> findByDoctorDoctorId(Long doctorId);
    List<Notification> findByIsReadFalse();
    List<Notification> findByStatus(String status);
    
    @Query("SELECT n FROM Notification n WHERE n.patient.patientId = :patientId AND n.isRead = false ORDER BY n.createdAt DESC")
    List<Notification> getUnreadPatientNotifications(@Param("patientId") Long patientId);
    
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.patient.patientId = :patientId AND n.isRead = false")
    Long getUnreadNotificationCount(@Param("patientId") Long patientId);
}

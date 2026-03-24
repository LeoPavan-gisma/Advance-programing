package com.gisma.pams.repository;

import com.gisma.pams.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByPhoneNumber(String phoneNumber);
    Optional<Doctor> findByEmail(String email);
    List<Doctor> findBySpecialty(String specialty);
    List<Doctor> findByDepartment(String department);
    
    @Query("SELECT d FROM Doctor d WHERE LOWER(d.firstName) LIKE LOWER(CONCAT('%', :name, '%')) " +
           "OR LOWER(d.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Doctor> searchByName(@Param("name") String name);
    
    @Query("SELECT d FROM Doctor d WHERE d.specialty = :specialty AND d.department = :department")
    List<Doctor> findBySpecialtyAndDepartment(@Param("specialty") String specialty, @Param("department") String department);
}

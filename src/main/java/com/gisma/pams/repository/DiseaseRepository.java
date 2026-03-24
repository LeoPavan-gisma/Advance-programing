package com.gisma.pams.repository;

import com.gisma.pams.model.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {
    Optional<Disease> findByDiseaseName(String diseaseName);
    
    @Query("SELECT d FROM Disease d WHERE LOWER(d.diseaseName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Disease> searchByName(@Param("name") String name);
}

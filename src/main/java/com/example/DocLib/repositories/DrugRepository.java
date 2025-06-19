package com.example.DocLib.repositories;

import com.example.DocLib.models.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugRepository extends JpaRepository<Drug,Long> {

    // Simple search by name containing (case-insensitive)
    List<Drug> findByNameContainingIgnoreCase(String keyword);

    // Optional: more flexible custom query
    @Query("SELECT d FROM Drug d WHERE " +
            "LOWER(d.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(d.scientificName) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Drug> searchDrugs(@Param("query") String query);
}

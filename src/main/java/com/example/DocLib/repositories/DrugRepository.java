package com.example.DocLib.repositories;

import com.example.DocLib.models.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugRepository extends JpaRepository<Drug,Long> {

}

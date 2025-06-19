package com.example.DocLib.services.implementation;

import com.example.DocLib.models.Drug;
import com.example.DocLib.repositories.DrugRepository;
import com.example.DocLib.services.interfaces.DrugServices;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrugServicesImp implements DrugServices {
    private final DrugRepository drugRepository;

    @Autowired
    public DrugServicesImp(DrugRepository drugRepository) {
        this.drugRepository = drugRepository;
    }


    @Override
    @Cacheable(value = "availableDrug",  key = "'all'")
    public List<Drug> getAllDrugs() {
        return drugRepository.findAll();
    }

    @Override
    public Drug getDrugByID(Long drugId) {
        return drugRepository.findById(drugId)
                .orElseThrow(() -> new EntityNotFoundException("Drug With ID:"+drugId+"Not Found."));
    }

    public List<Drug> searchDrugs(String query) {
        return drugRepository.searchDrugs(query);
    }

    @Override
    @CacheEvict(value = "availableDrug" ,allEntries = true  )
    public Drug addDrug(Drug drug){
        return drugRepository.save(drug);
    }
}

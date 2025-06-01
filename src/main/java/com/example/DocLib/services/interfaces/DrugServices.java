package com.example.DocLib.services.interfaces;

import com.example.DocLib.models.Drug;

import java.util.List;

public interface DrugServices {
    List<Drug> getAllDrugs();
    Drug getDrugByID(Long drugId);
    Drug addDrug(Drug drug);
}

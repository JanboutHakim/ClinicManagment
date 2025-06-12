package com.example.DocLib.services.implementation;

import com.example.DocLib.models.Drug;
import com.example.DocLib.repositories.DrugRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DrugServicesImpTest {
    @Test
    void getAllDrugsReturnsAll() {
        DrugRepository repo = mock(DrugRepository.class);
        DrugServicesImp service = new DrugServicesImp(repo);
        List<Drug> drugs = List.of(new Drug(), new Drug());
        when(repo.findAll()).thenReturn(drugs);

        List<Drug> result = service.getAllDrugs();

        assertEquals(drugs, result);
    }

    @Test
    void getDrugByIdReturnsDrug() {
        DrugRepository repo = mock(DrugRepository.class);
        DrugServicesImp service = new DrugServicesImp(repo);
        Drug drug = new Drug();
        when(repo.findById(1L)).thenReturn(Optional.of(drug));

        Drug result = service.getDrugByID(1L);

        assertSame(drug, result);
    }

    @Test
    void addDrugSavesDrug() {
        DrugRepository repo = mock(DrugRepository.class);
        DrugServicesImp service = new DrugServicesImp(repo);
        Drug drug = new Drug();
        when(repo.save(drug)).thenReturn(drug);

        Drug result = service.addDrug(drug);

        assertSame(drug, result);
        verify(repo).save(drug);
    }
}

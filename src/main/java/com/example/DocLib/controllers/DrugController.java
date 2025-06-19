package com.example.DocLib.controllers;

import com.example.DocLib.models.Drug;
import com.example.DocLib.services.implementation.DrugServicesImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/drugs")
public class DrugController {
    private final DrugServicesImp drugServicesImp;
    public DrugController(DrugServicesImp drugServicesImp){
        this.drugServicesImp=drugServicesImp;
    }

    @GetMapping("")
    public ResponseEntity<List<Drug>> getAllDrugs(){
        List<Drug> drugs = drugServicesImp.getAllDrugs();
        return ResponseEntity.ok(drugs);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Drug>> searchDrugs(@RequestParam String q) {
        return ResponseEntity.ok(drugServicesImp.searchDrugs(q));
    }


}

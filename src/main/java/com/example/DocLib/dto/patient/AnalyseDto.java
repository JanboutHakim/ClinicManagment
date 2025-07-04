package com.example.DocLib.dto.patient;

import com.example.DocLib.enums.Analyses;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Data
public class AnalyseDto {


        @NotNull
        private Long id;

        @NotNull
        private Analyses analyses;

        @NotNull
        @Size(min = 1, max = 100)
        private String filepath;

        @Size(max = 500)
        private String description;

        @PastOrPresent
        private Date date;

        @NotNull
        private Long patientId;

}
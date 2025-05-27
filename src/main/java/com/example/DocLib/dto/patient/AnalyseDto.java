package com.example.DocLib.dto.patient;

import com.example.DocLib.enums.Analyses;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnalyseDto {


        @NotNull
        private Long id;

        @NotNull
        private Analyses analyses;

        @NotNull
        @Size(min = 1, max = 100)
        private String result;

        @Size(max = 500)
        private String description;

        @PastOrPresent
        private Date date;

        @NotNull
        private Long patientId;

}
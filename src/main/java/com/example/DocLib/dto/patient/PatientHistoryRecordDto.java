package com.example.DocLib.dto.patient;

import com.example.DocLib.enums.HistoryRecordTypes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

@Data
public class PatientHistoryRecordDto {
    @NotNull(message = "ID is required")
    private Long id;

    @NotNull(message = "History Record Type is required")
    private HistoryRecordTypes historyRecordType;

    @NotBlank(message = "Record Detail is required")
    @Size(max = 500, message = "Record Detail must be maximum 500 characters long")
    private String recordDetail;

    @NotNull(message = "Date is required")
    @PastOrPresent(message = "Date should be in the past or present")
    private Date date;

    @NotNull(message = "Patient ID is required")
    private Long patientId;
}
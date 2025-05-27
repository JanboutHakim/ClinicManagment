// PatientDrugId.java
package com.example.DocLib.models.compostionKey;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PatientDrugId implements Serializable {
    private Long patientId;
    private Long drugId;
}

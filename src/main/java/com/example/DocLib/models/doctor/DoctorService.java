package com.example.DocLib.models.doctor;

import com.example.DocLib.enums.Services;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

@Entity
@Table(name = "service")
public class DoctorService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="doctor_id", nullable=false)
    private Doctor doctor;

    @Column(name = "services")
    @Enumerated
    private Services service;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DoctorService)) return false;
        DoctorService that = (DoctorService) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

package com.example.DocLib.models.doctor;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Specializations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="doctor_id", nullable=false)
    private Doctor doctor;
    @Column(nullable = false)
    private String specialization;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Specializations)) return false;
        Specializations that = (Specializations) o;
        return getId() != null && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}

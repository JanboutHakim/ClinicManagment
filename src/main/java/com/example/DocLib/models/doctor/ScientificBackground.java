package com.example.DocLib.models.doctor;

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
@Table(name = "scientific_background")
public class ScientificBackground {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="doctor_id", nullable=false)
    private Doctor doctor;


    @Column(name = "field_of_study")
    private String fieldOfStudy;

    @Column(name = "unversity")
    private String university;

    @Column(name = "degree")
    private String degree;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScientificBackground)) return false;
        ScientificBackground that = (ScientificBackground) o;
        return getId() != null && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

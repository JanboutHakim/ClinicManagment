package com.example.DocLib.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

@Entity
@Table(name = "scientific_background")
public class ScientificBackground {


    @Id
    @ManyToOne
    @JoinColumn(name="doctor_id", nullable=false)
    private Doctor doctor;


    @Column(name = "field_of_study")
    private String fieldOfStudy;

    @Column(name = "unversity")
    private String university;

    @Column(name = "degree")
    private String degree;

}

package com.example.DocLib.dto.doctor;


import com.example.DocLib.models.doctor.Doctor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ScientificBackgroundDto {

    private Long doctorId;
    private Long id;

    @NotNull(message = "Doctor cannot be null")
    private Doctor doctor;


    @Size(min = 2, max = 100, message = "Field of Study must be between 2 and 100 characters long")
    @NotNull(message = "Field of Study cannot be null")
    private String fieldOfStudy;

    @Size(min = 2, max = 100, message = "University must be between 2 and 100 characters long")
    @NotNull(message = "University cannot be null")
    private String university;

    @Size(min = 2, max = 100, message = "Degree must be between 2 and 100 characters long")
    @NotNull(message = "Degree cannot be null")
    private String degree;
}

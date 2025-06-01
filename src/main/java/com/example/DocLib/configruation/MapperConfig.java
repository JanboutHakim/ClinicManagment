package com.example.DocLib.configruation;

import com.example.DocLib.dto.doctor.*;
import com.example.DocLib.models.doctor.*;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MapperConfig {

    @Bean
    @Primary
    public ModelMapper modelMapperWithoutId() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(SpecializationsDto.class, Specializations.class)
                .addMappings(mapper -> mapper.skip(Specializations::setId));
        modelMapper.typeMap(ExperienceDto.class, Experience.class)
                .addMappings(mapper -> mapper.skip(Experience::setId));
        modelMapper.typeMap(ScientificBackgroundDto.class, ScientificBackground.class)
                .addMappings(mapper -> mapper.skip(ScientificBackground::setId));
        modelMapper.typeMap(DoctorServiceDto.class, DoctorService.class)
                .addMappings(mapper -> mapper.skip(DoctorService::setId));
        modelMapper.typeMap(DoctorScheduleDto.class, DoctorSchedule.class)
                .addMappings(mapper -> mapper.skip(DoctorSchedule::setId));
        modelMapper.typeMap(DoctorHolidayScheduleDto.class, DoctorHolidaySchedule.class)
                .addMappings(mapper -> mapper.skip(DoctorHolidaySchedule::setId));

        return modelMapper;
    }

    @Bean(name = "modelMapperWithId")
    public ModelMapper modelMapperWithId() {
        return new ModelMapper(); // no custom skips — maps everything, including id
    }
}

package com.example.DocLib.dto;

import com.example.DocLib.enums.InsuranceCompanies;
import lombok.*;
import jakarta.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Data
public class InsuranceCompanyDto {

    @NotNull(message = "Insurance ID is required")
    private Long insuranceId;

    @NotNull(message = "Company name is required")
    private InsuranceCompanies companyName;

}
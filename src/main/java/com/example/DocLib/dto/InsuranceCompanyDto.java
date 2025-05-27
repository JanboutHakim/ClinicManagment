package com.example.DocLib.dto;

import com.example.DocLib.enums.InsuranceCompanies;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Data
public class InsuranceCompanyDto {

    private Long insuranceId;
    private InsuranceCompanies companyName;

}
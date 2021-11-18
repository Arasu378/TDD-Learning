package com.arasu.tdd.TDDLearning.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryRequest {
    private String name;
    private Long countryId;
    private Boolean independent;
    private String region;
    private String capital;
}

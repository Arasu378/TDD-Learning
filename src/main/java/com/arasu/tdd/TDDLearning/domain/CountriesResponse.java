package com.arasu.tdd.TDDLearning.domain;

import com.arasu.tdd.TDDLearning.entities.Country;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountriesResponse {
    private Boolean success;
    private String message;
    private Country country;
    private List<Country> countries;
}

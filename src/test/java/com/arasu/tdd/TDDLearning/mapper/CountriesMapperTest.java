package com.arasu.tdd.TDDLearning.mapper;

import com.arasu.tdd.TDDLearning.domain.CountriesResponse;
import com.arasu.tdd.TDDLearning.domain.CountryRequest;
import com.arasu.tdd.TDDLearning.entities.Country;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CountriesMapperTest {
    private CountriesMapper countriesMapper = new CountriesMapper();

    @Test
    public void mapToCountriesResponse() {
        // Given
        Country country = getCountry();
        // When
        CountriesResponse countriesResponse = countriesMapper.mapToCountriesResponse(Collections.singletonList(country), "Countries");
        // Then
        assertThat(countriesResponse.getSuccess(), is(true));
        assertThat(countriesResponse.getCountries(), is(Collections.singletonList(country)));
        assertThat(countriesResponse.getMessage(), is("Countries"));
    }

    @Test
    public void mapToCountryResponse() {
        // Given
        Country country = getCountry();
        // When
        CountriesResponse countriesResponse = countriesMapper.mapToCountryResponse(country, "Country");
        // Then
        assertThat(countriesResponse.getSuccess(), is(true));
        assertThat(countriesResponse.getCountry(), is(country));
        assertThat(countriesResponse.getMessage(), is("Country"));
    }

    @Test
    public void mapToCountryEntity() {
        // Given
        CountryRequest countryRequest = getCountryRequest();
        // When
        Country country = countriesMapper.mapToCountryEntity(countryRequest);
        // Then
        assertThat(country.getIndependent(), is(true));
        assertThat(country.getCapital(), is("Delhi"));
        assertThat(country.getName(), is("India"));
    }
    private CountryRequest getCountryRequest() {
        return CountryRequest.builder()
                .capital("Delhi")
                .independent(true)
                .name("India")
                .region("Asia")
                .build();
    }
    private Country getCountry() {
        return Country.builder()
                .name("India")
                .capital("Delhi")
                .region("Asia")
                .independent(true)
                .build();
    }

}

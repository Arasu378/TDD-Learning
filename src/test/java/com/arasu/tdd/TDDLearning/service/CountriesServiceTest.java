package com.arasu.tdd.TDDLearning.service;

import com.arasu.tdd.TDDLearning.domain.CountriesResponse;
import com.arasu.tdd.TDDLearning.domain.CountryRequest;
import com.arasu.tdd.TDDLearning.entities.Country;
import com.arasu.tdd.TDDLearning.mapper.CountriesMapper;
import com.arasu.tdd.TDDLearning.repository.CountriesRepository;
import com.arasu.tdd.TDDLearning.utils.ExternalAPICall;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
//@ActiveProfiles({"qa", "local", "mock-so"})
@TestPropertySource(properties = {
        "countryName=India"
})
public class CountriesServiceTest {
    @InjectMocks
    private CountriesService classUnderTest;
    @Mock
    private CountriesRepository countriesRepository;
    @Mock
    private CountriesMapper mapper;
    @Mock
    private ExternalAPICall externalAPICall;

    @Test
    public void createCountryTest() {
        // Given
        CountryRequest countryRequest = getCountryRequest();
        Country country = getCountry();
        CountriesResponse countryResponse = getCountryResponse();
        // When
        when(mapper.mapToCountryEntity(countryRequest)).thenReturn(country);
        when(countriesRepository.save(country)).thenReturn(country);
        when(mapper.mapToCountryResponse(country, "Country saved successfully")).thenReturn(countryResponse);
        // doNothing().when(countriesRepository).save(any());
        CountriesResponse response = classUnderTest.createCountry(countryRequest);
        // Then
        assertThat(response.getSuccess(), is(true));
        assertThat(response.getCountry().getName(), is("India"));
        verify(countriesRepository, times(1)).save(any());
    }

    @Test
    public void getAllCountries() {
        // Given
        Country country = getCountry();
        CountriesResponse countryResponse = getCountryResponse();
        // When
        when(countriesRepository.findAll()).thenReturn(Collections.singletonList(country));
        when(mapper.mapToCountriesResponse(Collections.singletonList(country), "All Countries")).thenReturn(countryResponse);
        CountriesResponse response = classUnderTest.getAllCountries();
        // Then
        assertThat(response.getSuccess(), is(true));
        assertThat(response.getCountries(), hasSize(1));
        assertThat(response.getCountries(), is(Collections.singletonList(country)));

    }
     @Test
        public void getCountryById() {
            // Given
            Country country = getCountry();
            CountriesResponse countryResponse = getCountryResponse();
            // When
            when(countriesRepository.findById(anyLong())).thenReturn(Optional.of(country));
            when(mapper.mapToCountryResponse(country, "Country")).thenReturn(countryResponse);
            CountriesResponse response = classUnderTest.getCountryById(1L);
            // Then
            assertThat(response.getSuccess(), is(true));
            assertThat(response.getCountries(), hasSize(1));
            assertThat(response.getCountries(), is(Collections.singletonList(country)));

        }

    @Test
    public void getAllCountriesByExternalApi() {
        // Given
        Country country = getCountry();
        CountriesResponse countryResponse = getCountryResponse();
        // When
        when(externalAPICall.getAllCountries()).thenReturn(Collections.singletonList(country));
        when(mapper.mapToCountriesResponse(Collections.singletonList(country), "All Countries")).thenReturn(countryResponse);
        CountriesResponse response = classUnderTest.getAllCountriesByExternalApi();
        // Then
        assertThat(response.getSuccess(), is(true));
        assertThat(response.getCountries(), hasSize(1));
        assertThat(response.getCountries(), is(Collections.singletonList(country)));

    }

    private CountriesResponse getCountryResponse() {
        return CountriesResponse.builder()
                .country(getCountry())
                .countries(Collections.singletonList(getCountry()))
                .message("")
                .success(true)
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

    private CountryRequest getCountryRequest() {
        return CountryRequest.builder()
                .capital("Delhi")
                .independent(true)
                .name("India")
                .region("Asia")
                .build();
    }
}

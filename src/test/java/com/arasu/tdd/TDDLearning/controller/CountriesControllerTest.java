package com.arasu.tdd.TDDLearning.controller;

import com.arasu.tdd.TDDLearning.domain.CountriesResponse;
import com.arasu.tdd.TDDLearning.domain.CountryRequest;
import com.arasu.tdd.TDDLearning.entities.Country;
import com.arasu.tdd.TDDLearning.service.CountriesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
//@WebMvcTest(CountriesController.class)
public class CountriesControllerTest {
    @InjectMocks
    private CountriesController classUnderTest;
    @Mock
    private CountriesService countriesService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void buildMockMvc() {
        // MockMvc standalone approach
        mockMvc = MockMvcBuilders.standaloneSetup(classUnderTest)
                //  Add custom Advices and Filters manually and control each
                //        .setControllerAdvice(new FraudControllerAdvice())
                //       .addFilters(new FraudCheckerFilter())
                .build();
    }

    @Test
    public void createCountryAndExceptsSuccessResponse() throws Exception {
        // Given
        CountryRequest countryRequest = getCountryRequest();
        CountriesResponse countriesResponse = getCountryResponse();

        // When
        when(countriesService.createCountry(countryRequest)).thenReturn(countriesResponse);
        // Then
        mockMvc.perform(post("/country")
                        .header("Accept", "application/json")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(countryRequest)))
                .andDo((print())).andExpect(status().isOk());
    }

    @Test
    public void getCountryAndExceptsSuccessResponse() throws Exception {
        // Given
        CountriesResponse countriesResponse = getCountryResponse();

        // When
        when(countriesService.getAllCountries()).thenReturn(countriesResponse);
        // Then
        mockMvc.perform(get("/countries")
                        .header("Accept", "application/json")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo((print())).andExpect(status().isOk());
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

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

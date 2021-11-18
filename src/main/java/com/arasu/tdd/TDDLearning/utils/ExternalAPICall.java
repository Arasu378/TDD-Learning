package com.arasu.tdd.TDDLearning.utils;

import com.arasu.tdd.TDDLearning.domain.ExternalApiResponse;
import com.arasu.tdd.TDDLearning.entities.Country;
import com.arasu.tdd.TDDLearning.mapper.CountriesMapper;
import com.arasu.tdd.TDDLearning.service.CountriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Component
public class ExternalAPICall {
    @Value("${service.countriesAPI}")
    private String countriesUrl;
    private final RestTemplate restTemplate;
    private static final Logger LOG = Logger.getLogger(CountriesService.class.getName());
    private final CountriesMapper mapper;

    @Autowired
    public ExternalAPICall(CountriesMapper mapper, RestTemplate restTemplate) {
        this.mapper = mapper;
        this.restTemplate = restTemplate;
    }

    public List<Country> getAllCountries() {
        HttpHeaders httpHeaders = new HttpHeaders();
        LOG.info(() -> String.format("API URL  %s", countriesUrl));

        ParameterizedTypeReference<List<ExternalApiResponse>> countryResponse= new ParameterizedTypeReference<List<ExternalApiResponse>>() {};
        try {
            httpHeaders.set("Accept", "application/json");
            HttpEntity <String> entity = new HttpEntity<>(httpHeaders);
            ResponseEntity<List<ExternalApiResponse>> responseEntity = restTemplate.exchange(countriesUrl, HttpMethod.GET, entity, countryResponse);
            List<ExternalApiResponse> body = responseEntity.getBody();
            if (body != null) {
                return mapper.mapApiResponseToList(body);
            }
        } catch (Exception e) {
            LOG.info(() -> String.format("Exception in Calling API %s", e.getMessage()));
        }
        return Collections.emptyList();
    }
}

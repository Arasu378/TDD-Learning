package com.arasu.tdd.TDDLearning.service;

import com.arasu.tdd.TDDLearning.domain.CountriesResponse;
import com.arasu.tdd.TDDLearning.domain.CountryRequest;
import com.arasu.tdd.TDDLearning.entities.Country;
import com.arasu.tdd.TDDLearning.mapper.CountriesMapper;
import com.arasu.tdd.TDDLearning.repository.CountriesRepository;
import com.arasu.tdd.TDDLearning.utils.ExternalAPICall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class CountriesService {
    private final CountriesRepository countriesRepository;
    private final CountriesMapper mapper;
    private final ExternalAPICall externalAPICall;
    private static final Logger LOG = Logger.getLogger(CountriesService.class.getName());
    @Value("${countryName}")
    private String countriesUrl;

    @Autowired
    public CountriesService(CountriesRepository countriesRepository, CountriesMapper mapper, ExternalAPICall externalAPICall) {
        this.countriesRepository = countriesRepository;
        this.mapper = mapper;
        this.externalAPICall = externalAPICall;
    }

    public CountriesResponse createCountry(CountryRequest countryRequest) {
        LOG.info(() -> String.format("Create Country Service %s", countryRequest.toString()));
        Country country = mapper.mapToCountryEntity(countryRequest);
        Country savedCountryEntity = countriesRepository.save(country);
        return mapper.mapToCountryResponse(savedCountryEntity, "Country saved successfully");
    }

    public CountriesResponse updateCountry(CountryRequest countryRequest) {
        LOG.info(() -> String.format("Update Country Service %s", countryRequest.toString()));
        Country country = mapper.mapToCountryEntity(countryRequest);
        Country savedCountryEntity = countriesRepository.save(country);
        return mapper.mapToCountryResponse(savedCountryEntity, "Updated country successfully");
    }

    public CountriesResponse getCountryById(Long countryId) {
        Optional<Country> country = countriesRepository.findById(countryId);
        return country.map(value -> mapper.mapToCountryResponse(value, "Country")).orElse(null);
    }

    public CountriesResponse getAllCountries() {
        List<Country> countries = countriesRepository.findAll();
        return mapper.mapToCountriesResponse(countries, "All Countries");
    }
    public CountriesResponse getAllCountriesByExternalApi() {
        List<Country> countries = externalAPICall.getAllCountries();
        return mapper.mapToCountriesResponse(countries, "All Countries");
    }
}

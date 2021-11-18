package com.arasu.tdd.TDDLearning.controller;

import com.arasu.tdd.TDDLearning.domain.CountriesResponse;
import com.arasu.tdd.TDDLearning.domain.CountryRequest;
import com.arasu.tdd.TDDLearning.service.CountriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController()
public class CountriesController {
    private static final Logger LOG = Logger.getLogger(CountriesController.class.getName());
    private final CountriesService countriesService;

    @Autowired
    public CountriesController(CountriesService countriesService) {
        this.countriesService = countriesService;
    }

    @PostMapping("/country")
    public ResponseEntity<CountriesResponse> createCountry(@RequestBody CountryRequest country) {
        LOG.info(() -> String.format("Create Country  %s", country.toString()));
        CountriesResponse response = countriesService.createCountry(country);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/country")
    public ResponseEntity<CountriesResponse> updateCountry(@RequestBody CountryRequest country) {
        LOG.info(() -> String.format("Update Country  %s", country.toString()));
        CountriesResponse response = countriesService.updateCountry(country);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/country/{countryId}")
    public ResponseEntity<CountriesResponse> getCountryById(@PathVariable("countryId") Long countryId) {
        LOG.info(() -> String.format("getCountryById  %s", countryId));
        CountriesResponse response = countriesService.getCountryById(countryId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/countries")
    public ResponseEntity<CountriesResponse> getAllCountries() {
        LOG.info(() -> "getAllCountries");
        CountriesResponse response = countriesService.getAllCountries();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/countriesByExternalApi")
    public ResponseEntity<CountriesResponse> getAllCountriesByExternalApi() {
        LOG.info(() -> "getAllCountries");
        CountriesResponse response = countriesService.getAllCountriesByExternalApi();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

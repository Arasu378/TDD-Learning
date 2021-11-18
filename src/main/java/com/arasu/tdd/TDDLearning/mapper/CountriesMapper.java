package com.arasu.tdd.TDDLearning.mapper;

import com.arasu.tdd.TDDLearning.domain.CountriesResponse;
import com.arasu.tdd.TDDLearning.domain.CountryRequest;
import com.arasu.tdd.TDDLearning.domain.ExternalApiResponse;
import com.arasu.tdd.TDDLearning.entities.Country;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class CountriesMapper {

    public Country mapToCountryEntity(CountryRequest countryRequest) {
        Country country = new Country();
        if (countryRequest.getCountryId() != null && countryRequest.getCountryId() != 0) {
            country.setCountryId(countryRequest.getCountryId());
        }
        country.setCapital(countryRequest.getCapital());
        country.setIndependent(countryRequest.getIndependent());
        country.setName(countryRequest.getName());
        country.setRegion(countryRequest.getRegion());
        return country;
    }
    public CountriesResponse mapToCountryResponse(Country savedCountryEntity, String message) {
        CountriesResponse response = new CountriesResponse();
        response.setCountry(savedCountryEntity);
        response.setMessage(message);
        response.setSuccess(true);
        return response;
    }
    public CountriesResponse mapToCountriesResponse(List<Country> savedCountryEntity, String message) {
        CountriesResponse response = new CountriesResponse();
        response.setCountries(savedCountryEntity);
        response.setMessage(message);
        response.setSuccess(true);
        return response;
    }

    public List<Country> mapApiResponseToList(List<ExternalApiResponse> body) {
        List<Country> countries = new ArrayList<>();
        body.forEach(data ->  {
            Country country = new Country();
            country.setName(data.getName().getCommon());
            if (!CollectionUtils.isEmpty(data.getCapital())) {
                country.setCapital(data.getCapital().get(0));
            }
            country.setRegion(data.getRegion());
            country.setIndependent(data.getIndependent());
            countries.add(country);
        });
        return countries;
    }
}

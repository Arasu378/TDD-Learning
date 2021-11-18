package com.arasu.tdd.TDDLearning.repository;

import com.arasu.tdd.TDDLearning.entities.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
//@Tag("IntegrationTest")
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CountriesRepositoryTest {
    @Autowired
    private CountriesRepository countriesRepository;
    private final Long ID = 1L;

    @Test
    public void dependenciesAreInjected() {
        assertThat(countriesRepository, notNullValue());
    }

    @Test
    public void findsNoTransactionsInAnEmptyRepository() {
        assertThat(countriesRepository.findAll(), hasSize(0));
    }

    @Test
    public void findingByIdInAnEmptyRepositoryYieldsNothing() {
        assertThat(countriesRepository.findById(1L), is(Optional.empty()));
    }

    @Test
    public void savesATransaction() {
        // When
        countriesRepository.save(getCountry());
        // Then
        assertThat(countriesRepository.findAll(), hasSize(1));

        Optional<Country> country = countriesRepository.findById(1L);
        country.ifPresent(value -> assertThat(value, is(getCountry())));
    }

    @Test
    public void deletesATransaction() {
        // Given
        countriesRepository.save(getCountry());
        Optional<Country> country = countriesRepository.findById(1L);
        if (country.isPresent()) {
            assert country.get().equals(getCountry());
            // When
            countriesRepository.delete(country.get());

            // Then
            assertThat(countriesRepository.findAll(), hasSize(0));
            assertThat(countriesRepository.findById(1L), is(Optional.empty()));
        }
    }


    @Test
    public void shoutsWhenDeletingByNonExistentId() {
        assertThrows(org.springframework.dao.EmptyResultDataAccessException.class,
                () -> countriesRepository.deleteById(1L),
                String.format("Country entity with id %s exists!", 1L));
    }

    private Country getCountry() {
        return Country.builder()
                .countryId(1L)
                .name("India")
                .capital("Delhi")
                .region("Asia")
                .independent(true)
                .build();
    }
}

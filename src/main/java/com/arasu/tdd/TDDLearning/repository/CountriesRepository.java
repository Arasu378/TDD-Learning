package com.arasu.tdd.TDDLearning.repository;

import com.arasu.tdd.TDDLearning.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountriesRepository extends JpaRepository<Country, Long> {
}

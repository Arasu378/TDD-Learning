package com.arasu.tdd.TDDLearning.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "COUNTRY")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long countryId;
    @Column(name = "NAME")
    private String name;
    @Column(name = "CAPITAL")
    private String capital;
    @Column(name = "REGION")
    private String region;
    @Column(name = "INDEPENDENT")
    private Boolean independent;

}

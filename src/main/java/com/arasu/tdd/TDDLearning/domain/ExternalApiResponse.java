package com.arasu.tdd.TDDLearning.domain;

import lombok.Data;

import java.util.List;

@Data
public class ExternalApiResponse {
    private Name name;
    private Boolean independent;
    private List<String> capital;
    private String region;

    @Data
    public class Name {
    private String common;
    }
}

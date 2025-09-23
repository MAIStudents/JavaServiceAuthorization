package ru.mai.lessons.rpks.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "enrichment_rules")
public class Enrichment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Positive
    private long id;

    @NotNull
    @Positive
    private long enrichmentId;

    @NotNull
    @Positive
    private long ruleId;

    @NotBlank
    private String fieldName;

    @NotBlank
    private String fieldNameEnrichment;

    @NotBlank
    private String fieldValue;

    @NotBlank
    private String fieldValueDefault;
}
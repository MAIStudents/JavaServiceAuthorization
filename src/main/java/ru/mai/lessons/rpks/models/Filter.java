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
@Table(name = "filter_rules")
public class Filter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Positive
    private long id;

    @NotNull
    @Positive
    private long filterId;

    @NotNull
    @Positive
    private long ruleId;

    @NotBlank
    private String fieldName;
    @NotBlank
    private String filterFunctionName;
    @NotBlank
    private String filterValue;
}
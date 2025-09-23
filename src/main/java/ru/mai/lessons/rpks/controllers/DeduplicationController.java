package ru.mai.lessons.rpks.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.mai.lessons.rpks.dto.request.DeduplicationRequest;
import ru.mai.lessons.rpks.dto.response.DeduplicationResponse;

@Tag(name = "Deduplication API", description = "API для управления правилами дедупликации")
@RequestMapping("/deduplication")
public interface DeduplicationController {

    @Operation(summary = "Получить все правила")
    @GetMapping("/findAll")
    Iterable<DeduplicationResponse> getAllDeduplications();

    @Operation(summary = "Получить правила по ID дедупликации")
    @GetMapping("/findAll/{id}")
    Iterable<DeduplicationResponse> getAllDeduplicationsByDeduplicationId(@PathVariable("id") long id);

    @Operation(summary = "Получить конкретное правило")
    @GetMapping("/findById/deduplication/{deduplicationId}/rule/{ruleId}")
    DeduplicationResponse getDeduplicationById(
            @PathVariable("deduplicationId") long deduplicationId,
            @PathVariable("ruleId") long ruleId);

    @Operation(summary = "Удалить все правила")
    @DeleteMapping("/delete")
    void deleteDeduplication();

    @Operation(summary = "Удалить конкретное правило")
    @DeleteMapping("/delete/deduplication/{deduplicationId}/rule/{ruleId}")
    void deleteDeduplicationById(
            @PathVariable("deduplicationId") long deduplicationId,
            @PathVariable("ruleId") long ruleId);

    @Operation(summary = "Создать новое правило")
    @PostMapping("/save")
    void save(@RequestBody @Valid DeduplicationRequest deduplication);
}
package ru.mai.lessons.rpks.clients;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.mai.lessons.rpks.dto.request.EnrichmentRequest;
import ru.mai.lessons.rpks.dto.response.EnrichmentResponse;

/**
 * Клиент для контролера обогащения
 */
@FeignClient(
    name = "enrichmentClient",
    url = "${feign.client.url.enrichment}"
)
public interface EnrichmentClient {
    @GetMapping("/findAll")
    Iterable<EnrichmentResponse> getAllEnrichmentRequests();

    @GetMapping("/findAll/{id}")
    Iterable<EnrichmentResponse> getAllEnrichmentRequestsByEnrichmentRequestId(
            @PathVariable("id") long id);

    @GetMapping("/find/{enrichmentId}/{ruleId}")
    EnrichmentResponse getEnrichmentRequestById(
            @PathVariable("enrichmentId") long enrichmentId,
            @PathVariable("ruleId") long ruleId);

    @DeleteMapping("/delete")
    void deleteEnrichmentRequest();

    @DeleteMapping("/delete/{enrichmentId}/{ruleId}")
    void deleteEnrichmentRequestById(
            @PathVariable("enrichmentId") long enrichmentId,
            @PathVariable("ruleId") long ruleId);

    @PostMapping("/save")
    void save(@RequestBody @Valid EnrichmentRequest enrichment);
}

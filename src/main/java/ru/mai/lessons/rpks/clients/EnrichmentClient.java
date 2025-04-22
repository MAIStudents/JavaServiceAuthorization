package ru.mai.lessons.rpks.clients;


import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
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
    Iterable<EnrichmentResponse> getAllEnrichments();

    @GetMapping("/findAll/{id}")
    Iterable<EnrichmentResponse> getAllEnrichmentsByEnrichmentId(@PathVariable long id);

    @GetMapping("/find/{enrichmentId}/{ruleId}")
    EnrichmentResponse getEnrichmentById(@PathVariable long enrichmentId, @PathVariable long ruleId);

    @DeleteMapping("/delete")
    void deleteEnrichment();

    @DeleteMapping("/delete/{enrichmentId}/{ruleId}")
    void deleteEnrichmentById(@PathVariable long enrichmentId, @PathVariable long ruleId);

    @PostMapping("/save")
    ResponseEntity<EnrichmentResponse> save(@RequestBody @Valid EnrichmentRequest enrichment);
}

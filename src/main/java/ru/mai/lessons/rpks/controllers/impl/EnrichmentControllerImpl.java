package ru.mai.lessons.rpks.controllers.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.mai.lessons.rpks.clients.EnrichmentClient;
import ru.mai.lessons.rpks.controllers.EnrichmentController;
import ru.mai.lessons.rpks.dto.request.EnrichmentRequest;
import ru.mai.lessons.rpks.dto.response.EnrichmentResponse;

@Validated
@RestController
@RequestMapping("/enrichment")
@RequiredArgsConstructor
public class EnrichmentControllerImpl implements EnrichmentController {

  private final EnrichmentClient enrichmentClient;

  @Override
  @GetMapping("/findAll")
  @ResponseStatus(value = HttpStatus.OK)
  public Iterable<EnrichmentResponse> getAllEnrichmentRequests() {
    return enrichmentClient.getAllEnrichmentRequests();
  }

  @Override
  @GetMapping("/findAll/{id}")
  @ResponseStatus(value = HttpStatus.OK)
  public Iterable<EnrichmentResponse> getAllEnrichmentRequestsByEnrichmentRequestId(
      @PathVariable("id") long id) {
    return enrichmentClient.getAllEnrichmentRequestsByEnrichmentRequestId(id);
  }

  @Override
  @GetMapping("/find/{enrichmentId}/{ruleId}")
  @ResponseStatus(value = HttpStatus.OK)
  public EnrichmentResponse getEnrichmentRequestById(
      @PathVariable("enrichmentId") long enrichmentId,
      @PathVariable("ruleId") long ruleId) {
    return enrichmentClient.getEnrichmentRequestById(enrichmentId, ruleId);
  }

  @Override
  @DeleteMapping("/delete")
  @ResponseStatus(value = HttpStatus.OK)
  public void deleteEnrichmentRequest() {
    enrichmentClient.deleteEnrichmentRequest();
  }

  @Override
  @DeleteMapping("/delete/{enrichmentId}/{ruleId}")
  @ResponseStatus(value = HttpStatus.OK)
  public void deleteEnrichmentRequestById(
      @PathVariable("enrichmentId") long enrichmentId,
      @PathVariable("ruleId") long ruleId) {
    enrichmentClient.deleteEnrichmentRequestById(enrichmentId, ruleId);
  }

  @Override
  @PostMapping("/save")
  @ResponseStatus(value = HttpStatus.CREATED)
  public void save(@RequestBody @Valid EnrichmentRequest enrichment) {
    enrichmentClient.save(enrichment);
  }
}

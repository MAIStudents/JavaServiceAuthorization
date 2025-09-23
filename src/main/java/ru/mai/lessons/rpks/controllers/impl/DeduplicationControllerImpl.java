package ru.mai.lessons.rpks.controllers.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.mai.lessons.rpks.controllers.DeduplicationController;
import ru.mai.lessons.rpks.dto.request.DeduplicationRequest;
import ru.mai.lessons.rpks.dto.response.DeduplicationResponse;
import ru.mai.lessons.rpks.services.DeduplicationService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DeduplicationControllerImpl implements DeduplicationController {

  private final DeduplicationService deduplicationService;

  @Override
  public Iterable<DeduplicationResponse> getAllDeduplications() {
    log.info("Getting all deduplication rules");
    return deduplicationService.getAllDeduplications();
  }

  @Override
  public Iterable<DeduplicationResponse> getAllDeduplicationsByDeduplicationId(long id) {
    log.info("Getting deduplication rules by deduplicationId: {}", id);
    return deduplicationService.getAllDeduplicationsByDeduplicationId(id);
  }

  @Override
  public DeduplicationResponse getDeduplicationById(long deduplicationId, long ruleId) {
    log.info("Getting deduplication rule: deduplicationId={}, ruleId={}", deduplicationId, ruleId);
    return deduplicationService.getDeduplicationById(deduplicationId, ruleId);
  }

  @Override
  public void deleteDeduplication() {
    log.info("Deleting all deduplication rules");
    deduplicationService.deleteDeduplication();
  }

  @Override
  public void deleteDeduplicationById(long deduplicationId, long ruleId) {
    log.info("Deleting deduplication rule: deduplicationId={}, ruleId={}", deduplicationId, ruleId);
    deduplicationService.deleteDeduplicationById(deduplicationId, ruleId);
  }

  @Override
  @ResponseStatus(HttpStatus.CREATED)
  public void save(@Valid @RequestBody DeduplicationRequest deduplication) {
    log.info("Creating new deduplication rule: {}", deduplication);
    deduplicationService.save(deduplication);
  }
}
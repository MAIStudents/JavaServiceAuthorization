package ru.mai.lessons.rpks.controllers.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.mai.lessons.rpks.controllers.DeduplicationController;
import ru.mai.lessons.rpks.dto.request.DeduplicationRequest;
import ru.mai.lessons.rpks.dto.response.DeduplicationResponse;
import ru.mai.lessons.rpks.services.DeduplicationService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/deduplication")
public class DeduplicationControllerImpl implements DeduplicationController {

  private final DeduplicationService deduplicationService;

  @Override
  @GetMapping("/findAll")
  @ResponseStatus(value = HttpStatus.OK)
  public Iterable<DeduplicationResponse> getAllDeduplications() {
    return deduplicationService.getAllDeduplications();
  }

  @Override
  @GetMapping("/findAll/{id}")
  @ResponseStatus(value = HttpStatus.OK)
  public Iterable<DeduplicationResponse> getAllDeduplicationsByDeduplicationId(
          @PathVariable("id") long id) {
    return deduplicationService.getAllDeduplicationsByDeduplicationId(id);
  }

  @Override
  @GetMapping("/find/{deduplicationId}/{ruleId}")
  @ResponseStatus(value = HttpStatus.OK)
  public DeduplicationResponse getDeduplicationById(
          @PathVariable("deduplicationId") long deduplicationId,
          @PathVariable("ruleId") long ruleId) {
    return deduplicationService.getDeduplicationById(deduplicationId, ruleId);
  }

  @Override
  @DeleteMapping("/delete")
  @ResponseStatus(value = HttpStatus.OK)
  public void deleteDeduplication() {
    deduplicationService.deleteDeduplication();
  }

  @Override
  @DeleteMapping("/delete/{deduplicationId}/{ruleId}")
  @ResponseStatus(value = HttpStatus.OK)
  public void deleteDeduplicationById(
          @PathVariable("deduplicationId") long deduplicationId,
          @PathVariable("ruleId") long ruleId) {
    deduplicationService.deleteDeduplicationById(deduplicationId, ruleId);
  }

  @Override
  @PostMapping("/save")
  @ResponseStatus(value = HttpStatus.CREATED)
  public void save(@RequestBody @Valid DeduplicationRequest deduplication) {
    deduplicationService.save(deduplication);
  }
}

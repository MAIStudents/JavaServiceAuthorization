package ru.mai.lessons.rpks.controllers.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.mai.lessons.rpks.clients.FilterClient;
import ru.mai.lessons.rpks.controllers.FilterController;
import ru.mai.lessons.rpks.dto.request.FilterRequest;
import ru.mai.lessons.rpks.dto.response.FilterResponse;

@Validated
@RestController
@RequestMapping("/filter")
@RequiredArgsConstructor
public class FilterControllerImpl implements FilterController {

  private final FilterClient filterClient;

  @Override
  @GetMapping("/findAll")
  @ResponseStatus(value = HttpStatus.OK)
  public Iterable<FilterResponse> getAllFilters() {
    return filterClient.getAllFilters();
  }

  @Override
  @GetMapping("/findAll/{id}")
  @ResponseStatus(value = HttpStatus.OK)
  public Iterable<FilterResponse> getAllFiltersByFilterId(
      @PathVariable("id") long id) {
    return filterClient.getAllFiltersByFilterId(id);
  }

  @Override
  @GetMapping("/find/{filterId}/{ruleId}")
  @ResponseStatus(value = HttpStatus.OK)
  public FilterResponse getFilterByFilterIdAndRuleId(
      @PathVariable("filterId") long filterId,
      @PathVariable("ruleId") long ruleId) {
    return filterClient.getFilterByFilterIdAndRuleId(filterId, ruleId);
  }

  @Override
  @DeleteMapping("/delete")
  @ResponseStatus(value = HttpStatus.OK)
  public void deleteFilter() {
    filterClient.deleteFilter();
  }

  @Override
  @DeleteMapping("/delete/{filterId}/{ruleId}")
  @ResponseStatus(value = HttpStatus.OK)
  public void deleteFilterById(
      @PathVariable("filterId") long filterId,
      @PathVariable("ruleId") long ruleId) {
    filterClient.deleteFilterById(filterId, ruleId);
  }

  @Override
  @PostMapping("/save")
  @ResponseStatus(value = HttpStatus.CREATED)
  public void save(@RequestBody @Valid FilterRequest filter) {
    filterClient.save(filter);
  }
}

package ru.mai.lessons.rpks.controllers.impl;

import jakarta.validation.Valid;
import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.mai.lessons.rpks.controllers.FilterController;
import ru.mai.lessons.rpks.dto.request.FilterRequest;
import ru.mai.lessons.rpks.dto.response.FilterResponse;

@Validated
@RestController
@RequestMapping("/filter")
public class FilterControllerImpl implements FilterController {

  @Override
  @GetMapping("/findAll")
  @ResponseStatus(value = HttpStatus.OK)
  public Iterable<FilterResponse> getAllFilters() {
    return Collections.emptyList();
  }

  @Override
  @GetMapping("/findAll/{id}")
  @ResponseStatus(value = HttpStatus.OK)
  public Iterable<FilterResponse> getAllFiltersByFilterId(
      @PathVariable("id") long id) {
    return Collections.emptyList();
  }

  @Override
  @GetMapping("/find/{filterId}/{ruleId}")
  @ResponseStatus(value = HttpStatus.OK)
  public FilterResponse getFilterByFilterIdAndRuleId(
      @PathVariable("filterId") long filterId,
      @PathVariable("ruleId") long ruleId) {
    return new FilterResponse();
  }

  @Override
  @DeleteMapping("/delete")
  @ResponseStatus(value = HttpStatus.OK)
  public void deleteFilter() {}

  @Override
  @DeleteMapping("/delete/{filterId}/{ruleId}")
  @ResponseStatus(value = HttpStatus.OK)
  public void deleteFilterById(
      @PathVariable("filterId") long filterId,
      @PathVariable("ruleId") long ruleId) {}

  @Override
  @PostMapping("/save")
  @ResponseStatus(value = HttpStatus.CREATED)
  public void save(@RequestBody @Valid FilterRequest filter) {}
}

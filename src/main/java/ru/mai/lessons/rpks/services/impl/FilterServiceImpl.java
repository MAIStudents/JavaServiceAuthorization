package ru.mai.lessons.rpks.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mai.lessons.rpks.clients.FilterClient;
import ru.mai.lessons.rpks.dto.request.FilterRequest;
import ru.mai.lessons.rpks.dto.response.FilterResponse;
import ru.mai.lessons.rpks.services.FilterService;

@Service
@RequiredArgsConstructor
public class FilterServiceImpl implements FilterService {

  private final FilterClient filterClient;

  @Override
  public Iterable<FilterResponse> getAllFilters() {
    return filterClient.getAllFilters();
  }

  @Override
  public Iterable<FilterResponse> getAllFiltersByFilterId(long id) {
    return filterClient.getAllFiltersByFilterId(id);
  }

  @Override
  public FilterResponse getFilterByFilterIdAndRuleId(long filterId, long ruleId) {
    return filterClient.getFilterByFilterIdAndRuleId(filterId, ruleId);
  }

  @Override
  public void deleteFilter() {
    filterClient.deleteFilter();
  }

  @Override
  public void deleteFilterById(long filterId, long ruleId) {
    filterClient.deleteFilterById(filterId, ruleId);
  }

  @Override
  public void save(FilterRequest filter) {
    filterClient.save(filter);
  }
}

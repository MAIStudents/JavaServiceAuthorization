package ru.mai.lessons.rpks.clients;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.mai.lessons.rpks.dto.request.FilterRequest;
import ru.mai.lessons.rpks.dto.response.FilterResponse;

/**
 * Клиент для контролера фильтрации
 */
@FeignClient(
    name = "filterClient",
    url = "${feign.client.url.filter}"
)
public interface FilterClient {
    @GetMapping("/findAll")
    Iterable<FilterResponse> getAllFilters();

    @GetMapping("/findAll/{id}")
    Iterable<FilterResponse> getAllFiltersByFilterId(
            @PathVariable("id") long id);

    @GetMapping("/find/{filterId}/{ruleId}")
    FilterResponse getFilterByFilterIdAndRuleId(
            @PathVariable("filterId") long filterId,
            @PathVariable("ruleId") long ruleId);

    @DeleteMapping("/delete")
    void deleteFilter();

    @DeleteMapping("/delete/{filterId}/{ruleId}")
    void deleteFilterById(
            @PathVariable("filterId") long filterId,
            @PathVariable("ruleId") long ruleId);

    @PostMapping("/save")
    void save(@RequestBody @Valid FilterRequest filter);
}

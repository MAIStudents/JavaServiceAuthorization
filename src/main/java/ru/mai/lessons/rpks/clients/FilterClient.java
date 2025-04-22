package ru.mai.lessons.rpks.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
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
    Iterable<FilterResponse> getAllFiltersByFilterId(@PathVariable long id);

    @GetMapping("/find/{filterId}/{ruleId}")
    FilterResponse getFilterByFilterIdAndRuleId(@PathVariable long filterId, @PathVariable long ruleId);

    @DeleteMapping("/delete")
    void deleteFilter();

    @DeleteMapping("/delete/{filterId}/{ruleId}")
    void deleteFilterById(@PathVariable long filterId, @PathVariable long ruleId);

    @PostMapping("/save")
    ResponseEntity<FilterResponse> save(@RequestBody FilterRequest filter);
}

package ru.mai.lessons.rpks.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.mai.lessons.rpks.dto.request.DeduplicationRequest;
import ru.mai.lessons.rpks.dto.response.DeduplicationResponse;

/**
 * Клиент для контролера дедубликации
 */
@FeignClient(
    name = "deduplicationClient",
    url = "${feign.client.url.deduplication}"
)
public interface DeduplicationClient {
    @GetMapping("/findAll")
    Iterable<DeduplicationResponse> getAllDeduplications();

    @GetMapping("/findAll/{id}")
    Iterable<DeduplicationResponse> getAllDeduplicationsByDeduplicationId(@PathVariable long id);

    @GetMapping("/find/{deduplicationId}/{ruleId}")
    DeduplicationResponse getDeduplicationById(@PathVariable long deduplicationId, @PathVariable long ruleId);

    @DeleteMapping("/delete")
    void deleteDeduplication() ;

    @DeleteMapping("/delete/{deduplicationId}/{ruleId}")
    void deleteDeduplicationById(@PathVariable long deduplicationId, @PathVariable long ruleId);

    @PostMapping("/save")
    @ResponseStatus(value = HttpStatus.CREATED)
    void save(@RequestBody DeduplicationRequest deduplication);
}

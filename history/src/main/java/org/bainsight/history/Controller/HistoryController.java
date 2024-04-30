package org.bainsight.history.Controller;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.bainsight.history.Data.HistoryServiceImpl;
import org.bainsight.history.Models.Dto.CandleStickDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bainsight/history")
@CircuitBreaker(name = "history-service")
@Retry(name = "history-service")
public class HistoryController {

    private final HistoryServiceImpl historyService;
    private final io.github.resilience4j.circuitbreaker.CircuitBreaker circuitBreaker;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{symbol}/{timeSpace}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<CandleStickDto> fetchHistory(@PathVariable String timeSpace,
                                             @PathVariable String symbol) {

        symbol = symbol.toUpperCase();
        timeSpace = timeSpace.toUpperCase();

        if(timeSpace.equals("1D"))
        {
            return this.historyService.fetchSticksForTheDay(symbol)
                    .transform(CircuitBreakerOperator.of(circuitBreaker));
        }
        else
        {
            return this.historyService.fetchByTime(symbol, timeSpace);
        }
    }


    @GetMapping("/losers_gainers")
    @ResponseStatus(HttpStatus.OK)
    public List<CandleStickDto> findLosersGainersForTheDay(){
        return this.historyService.findLosersGainersForTheDay();
    }


    @GetMapping("/stick/{symbol}")
    public CandleStickDto getCandleStickDto(@PathVariable final String symbol){
        return this.historyService.fetchCandleStick(symbol);
    }

}

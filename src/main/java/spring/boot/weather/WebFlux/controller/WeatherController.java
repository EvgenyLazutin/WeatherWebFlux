package spring.boot.weather.WebFlux.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import spring.boot.weather.WebFlux.dto.City;
import spring.boot.weather.WebFlux.service.WeatherService;


@RestController
@RequestMapping("/weather")
public class WeatherController {

    final private WeatherService weatherService;


    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping(value = "/{city}/{apiKey}")
    public Mono<City> getWeatherByNameCity(@PathVariable("city") final String city, @PathVariable("apiKey") final String apiKey) {
        Mono<City> response = weatherService.weatherByNameCity(city, apiKey);
        response.subscribe(System.out::println);
      return response;
    }
}

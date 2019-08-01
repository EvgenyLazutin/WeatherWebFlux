package spring.boot.weather.WebFlux.service;


import reactor.core.publisher.Mono;
import spring.boot.weather.WebFlux.dto.City;

public interface WeatherService {

    Mono<City> weatherByNameCity(String cityName, String apiKey);
}

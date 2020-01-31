package spring.boot.weather.WebFlux.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import spring.boot.weather.WebFlux.dto.City;

@Service
public class WeatherServiceImpl implements WeatherService{

    private final WebClient webClient;


    public WeatherServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openweathermap.org").build();
    }

    @Override
    public Mono<City> weatherByNameCity(String cityName, String apiKey) {
        return this.webClient.get().uri("/data/2.5/weather?q={cityName}&appid={apiKey}", cityName, apiKey)
                .retrieve().bodyToMono(City.class);
    }

    private  <T> T readJsonFile(String fileName, Class<T> responseClass) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(getClass().getResourceAsStream("/" + fileName), responseClass);
    }
}

package com.tek.pingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableScheduling
public class PingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PingServiceApplication.class, args);
    }

    /*
    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl("http://localhost:8080").build(); // Assuming Pong service is running on port 8081
    }

    @Scheduled(fixedDelay = 1000) // Send request every second
    public void sendRequest() {
        webClient().get()
                .uri("/pong")
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(response -> System.out.println("Received response from Pong: " + response),
                        error -> System.err.println("Error occurred: " + error.getMessage()));
    }*/
}

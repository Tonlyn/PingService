package com.tek.pingservice.handler;

import com.tek.pingservice.constant.ConfigValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PingHandler extends AbstractLockHandler {

    private static final Logger logger = LoggerFactory.getLogger(PingHandler.class);



    @Autowired
    private ConfigValue configValue;


    public WebClient webClient() {
        return WebClient.builder().baseUrl(configValue.pongBaseUrl).build(); // Assuming Pong service is running on port 8081
    }


    @Override
    void doBusiness() {
        String param = "Hello";
        String result = webClient().get()
                .uri("/pong?Hello")
                .retrieve()
                .onStatus(HttpStatus.OK::equals, resp -> {
                    logger.info("Request sent & Pong Respond.");
                    return Mono.empty();
                })
                .onStatus(HttpStatus.TOO_MANY_REQUESTS::equals, response -> {
                    logger.warn("Request sent & Pong throttled it.");
                    return Mono.empty();
                })
                .onStatus(httpStatus -> httpStatus.is4xxClientError() && !HttpStatus.TOO_MANY_REQUESTS.equals(httpStatus),
                        res -> {
                            return Mono.error(new RuntimeException("client error: " + res.statusCode().getReasonPhrase()));
                        })
                .onStatus(HttpStatus::is5xxServerError, resp -> {
                    return Mono.error(new RuntimeException("server error: " + resp.statusCode().getReasonPhrase()));
                })
                .bodyToMono(String.class)
                .block();

        //logger.info(">> Request sent: {} & Pong Respond: {}.", param, result);
    }

    @Override
    public void lockFail() {
        logger.warn("Request not send as being \"rate limited\".");
    }
}

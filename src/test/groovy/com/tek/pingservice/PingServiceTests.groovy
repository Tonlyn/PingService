package com.tek.pingservice

import com.tek.pingservice.controller.PingController
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.web.client.RestTemplate
import spock.lang.Specification


@SpringBootTest
class PingServiceTests extends Specification {
    @Autowired
    private PingController pingController

    @MockBean
    private RestTemplate restTemplate

    @Test
    void testSendPing() {
        given: "a successful response from Pong service"
        1 * restTemplate.getForObject(_, _) >> "World"

        when: "the ping is sent"
        pingController.sendPing()

        then: "it logs the success"
        // Add logging verification here if necessary
    }
}
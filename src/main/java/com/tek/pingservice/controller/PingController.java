package com.tek.pingservice.controller;

import com.tek.pingservice.handler.PingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class PingController {

    private static final Logger logger = LoggerFactory.getLogger(PingController.class);

    @Autowired
    private PingHandler pingHandler;



    //@Scheduled(fixedRate = 500, initialDelay = 5000)
    @Scheduled(cron = "0/1 * * * * ? ")
    public ResponseEntity<Void> sendPing() throws IOException {
        pingHandler.handle();
        return ResponseEntity.ok().build();
    }



}

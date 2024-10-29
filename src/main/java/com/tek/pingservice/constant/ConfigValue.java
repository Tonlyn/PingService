package com.tek.pingservice.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigValue {


    @Value("${pong-service.base.url:}")
    public String pongBaseUrl;


    @Value("${file.lock.dir:locks}")
    public String fileLockDir;

    @Value("${file.lock.fileName:fileLock}")
    public String fileLockFileName;
}

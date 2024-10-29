#!/bin/bash

# 启动第一个Ping服务实例
java -jar target/pingService-0.0.1-SNAPSHOT.jar --spring.application.name=ping-service-2 --server.port=8082 &

# 启动第二个Ping服务实例
java -jar target/pingService-0.0.1-SNAPSHOT.jar --spring.application.name=ping-service-3 --server.port=8083 &

# 启动第三个Ping服务实例
java -jar target/pingSrvice-0.0.1-SNAPSHOT.jar --spring.application.name=ping-service-4 --server.port=8084 &

echo "All Ping services started."

package com.project.port.client;

import com.project.port.config.CustomErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

/**
 * Client used for calling mocks of Space Hangar microservice for IT.
 * With Eureka service disabled for tests, it uses placeholder URL with port provided dynamically by WireMock server.
 */
@Profile("test")
@FeignClient(name = "${feign.clients.hangar.name}", path = "${feign.clients.hangar.path}",
    configuration = {CustomErrorDecoder.class}, url = "${feign.clients.hangar.url}")
public interface SpaceshipClientTest extends SpaceshipClientBase {

}

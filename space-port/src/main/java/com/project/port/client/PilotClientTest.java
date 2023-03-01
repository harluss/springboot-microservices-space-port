package com.project.port.client;

import com.project.port.config.CustomErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

/**
 * Client used for calling mocks of Space Cantina microservice for IT.
 * With Eureka service disabled for tests, it uses placeholder URL with port provided dynamically by WireMock server.
 */
@Profile("test")
@FeignClient(name = "${feign.clients.cantina.name}", path = "${feign.clients.cantina.path}",
    configuration = {CustomErrorDecoder.class}, url = "${feign.clients.cantina.url}")
public interface PilotClientTest extends PilotClientBase {

}

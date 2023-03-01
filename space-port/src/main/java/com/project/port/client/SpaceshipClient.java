package com.project.port.client;

import com.project.port.config.CustomErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

/**
 * Client used for calling Space Hangar microservice.
 */
@Profile("!test")
@FeignClient(name = "${feign.clients.hangar.name}", path = "${feign.clients.hangar.path}",
    configuration = {CustomErrorDecoder.class})
public interface SpaceshipClient extends SpaceshipClientBase {

}

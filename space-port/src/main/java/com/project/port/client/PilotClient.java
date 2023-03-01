package com.project.port.client;

import com.project.port.config.CustomErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

/**
 * Client used for calling Space Cantina microservice.
 */
@Profile("!test")
@FeignClient(name = "${feign.clients.cantina.name}", path = "${feign.clients.cantina.path}",
    configuration = {CustomErrorDecoder.class})
public interface PilotClient extends PilotClientBase {

}

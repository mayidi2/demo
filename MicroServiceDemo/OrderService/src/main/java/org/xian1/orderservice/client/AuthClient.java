package org.xian1.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.xian1.orderservice.dto.AuthValidationRequest;
import org.xian1.orderservice.dto.AuthValidationResponse;

@FeignClient(name = "auth-service")
public interface AuthClient {

    @PostMapping("/api/auth/validate")
    AuthValidationResponse validate(@RequestBody AuthValidationRequest request);
}

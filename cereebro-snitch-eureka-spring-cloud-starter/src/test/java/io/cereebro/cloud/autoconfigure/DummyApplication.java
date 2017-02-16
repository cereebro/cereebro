package io.cereebro.cloud.autoconfigure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;

@SpringBootApplication
@EnableFeignClients
public class DummyApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(DummyApplication.class, args);
    }

    @FeignClient(name = "dummy-api", url = "http://localhost", path = "/sample-api")
    public static interface AFeignClient {

    }

}

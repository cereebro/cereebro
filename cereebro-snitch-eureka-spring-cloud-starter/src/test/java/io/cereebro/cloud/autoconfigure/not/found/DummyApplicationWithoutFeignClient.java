package io.cereebro.cloud.autoconfigure.not.found;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DummyApplicationWithoutFeignClient {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(DummyApplicationWithoutFeignClient.class, args);
    }

}

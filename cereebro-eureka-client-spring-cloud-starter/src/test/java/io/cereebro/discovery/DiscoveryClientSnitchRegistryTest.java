package io.cereebro.discovery;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.core.SnitchRegistry;
import io.cereebro.discovery.DiscoveryClientSnitchRegistryTest.DiscoveryClientSnitchRegistryTestApplication;
import io.cereebro.spring.cloud.autoconfigure.discovery.DiscoveryClientSnitchRegistry;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DiscoveryClientSnitchRegistryTestApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class DiscoveryClientSnitchRegistryTest {

    @Autowired
    private SnitchRegistry registry;

    @Test
    public void test() {
        Assertions.assertThat(registry).isInstanceOf(DiscoveryClientSnitchRegistry.class);
    }

    @SpringBootApplication
    static class DiscoveryClientSnitchRegistryTestApplication {

        @MockBean
        DiscoveryClient discoveryClient;

    }

}

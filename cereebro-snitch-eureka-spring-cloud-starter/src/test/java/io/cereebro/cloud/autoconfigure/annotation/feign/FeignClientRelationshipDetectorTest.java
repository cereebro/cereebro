package io.cereebro.cloud.autoconfigure.annotation.feign;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Iterables;

import io.cereebro.cloud.autoconfigure.annotation.feign.FeignClientRelationshipDetectorTest.FeignClientTestApplication;
import io.cereebro.core.Relationship;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FeignClientTestApplication.class)
public class FeignClientRelationshipDetectorTest {

    @Autowired
    private FeignClientAnnotationRelationshipDetector detector;

    @Test
    public void testFeignClientIsFound() {
        Set<Relationship> relations = detector.detect();
        Assertions.assertThat(relations).isNotEmpty();
        Assertions.assertThat(relations).hasSize(1);
        Relationship relation = Iterables.getFirst(relations, null);
        Assertions.assertThat(relation.getComponent().getName()).isEqualTo("dummy-api");
        Assertions.assertThat(relation.getComponent().getType()).isEqualTo("WEBAPP");
    }

    @SpringBootApplication
    @EnableFeignClients
    static class FeignClientTestApplication {

        @FeignClient(name = "dummy-api", url = "http://localhost", path = "/sample-api")
        public static interface AFeignClient {

        }

    }

}

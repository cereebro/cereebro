package io.cereebro.cloud.autoconfigure.annotation.feign;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.cloud.autoconfigure.not.found.DummyApplicationWithoutFeignClient;
import io.cereebro.core.Relationship;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DummyApplicationWithoutFeignClient.class)
public class FeignClientRelationshipDetectorWithoutDeclaredClientTest {

    @Autowired
    private FeignClientAnnotationRelationshipDetector detector;

    @Test
    public void testFeignClientIsNotFound() {
        Set<Relationship> relations = detector.detect();
        Assertions.assertThat(relations).isEmpty();
    }

}

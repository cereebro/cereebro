package io.cereebro.cloud.autoconfigure.annotation.feign;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Iterables;

import io.cereebro.cloud.autoconfigure.DummyApplication;
import io.cereebro.core.Relationship;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DummyApplication.class, properties = "spring.application.name=whatever")
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

}

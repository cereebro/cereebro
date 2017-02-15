package io.cereebro.autoconfigure.annotation;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Iterables;

import io.cereebro.autoconfigure.DummyApplication;
import io.cereebro.core.Relationship;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DummyApplication.class)
@ActiveProfiles("annotation-bean")
public class DependencyHintAnnotationBeanRelationshipDetectorTest {

    @Autowired
    private DependencyHintAnnotationRelationshipDetector detector;

    @Test
    public void annotationRelationshipWithDeclaredBean() {
        Set<Relationship> relations = detector.detect();
        Assertions.assertThat(relations).isNotEmpty();
        Assertions.assertThat(relations).hasSize(1);
        Relationship dummyRelation = Iterables.getFirst(relations, null);
        Assertions.assertThat(dummyRelation.getComponent().getName()).isEqualTo("dummy");
        Assertions.assertThat(dummyRelation.getComponent().getType()).isEqualTo("ws");
    }

}

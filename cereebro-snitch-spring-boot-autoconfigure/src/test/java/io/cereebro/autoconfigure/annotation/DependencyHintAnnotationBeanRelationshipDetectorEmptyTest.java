package io.cereebro.autoconfigure.annotation;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.autoconfigure.DummyApplication;
import io.cereebro.core.Relationship;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DummyApplication.class)
public class DependencyHintAnnotationBeanRelationshipDetectorEmptyTest {

    @Autowired
    private DependencyHintAnnotationRelationshipDetector detector;

    @Test
    public void annotationRelationshipWithoutDeclaredBean() {
        Set<Relationship> relations = detector.detect();
        Assertions.assertThat(relations).isEmpty();
    }

}

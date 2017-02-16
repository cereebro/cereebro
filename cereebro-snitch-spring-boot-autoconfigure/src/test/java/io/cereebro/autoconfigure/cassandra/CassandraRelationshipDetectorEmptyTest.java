package io.cereebro.autoconfigure.cassandra;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.autoconfigure.DummyApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DummyApplication.class, properties = "spring.application.name=whatever")
public class CassandraRelationshipDetectorEmptyTest {

    @Autowired
    private CassandraRelationshipDetector detector;

    @Test
    public void cassandraRelationshipWithNoSessionAvailable() {
        Assertions.assertThat(detector.detect()).isEmpty();
    }

}

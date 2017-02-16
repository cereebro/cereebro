package io.cereebro.autoconfigure.cassandra;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.autoconfigure.DummyApplication;
import io.cereebro.core.Component;
import io.cereebro.core.Dependency;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DummyApplication.class, properties = "spring.application.name=whatever")
@ActiveProfiles("cassandra")
public class CassandraRelationshipDetectorTest {

    @Autowired
    private CassandraRelationshipDetector detector;

    @Test
    public void cassandraRelationshipWithSessionAvailable() {
        Assertions.assertThat(detector.detect()).isNotEmpty();
        Assertions.assertThat(detector.detect()).contains(Dependency.on(Component.of("keyspace", "CASSANDRA")));
    }

}

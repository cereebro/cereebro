package io.cereebro.spring.boot.autoconfigure.cassandra;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.spring.boot.autoconfigure.cassandra.CassandraRelationshipDetectorEmptyTest.MissingCassandraTestApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MissingCassandraTestApplication.class)
public class CassandraRelationshipDetectorEmptyTest {

    @Autowired
    private CassandraRelationshipDetector detector;

    @Test
    public void cassandraRelationshipWithNoSessionAvailable() {
        Assertions.assertThat(detector.detect()).isEmpty();
    }

    @SpringBootApplication
    static class MissingCassandraTestApplication {

    }

}

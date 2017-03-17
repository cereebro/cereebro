package io.cereebro.spring.boot.autoconfigure.cassandra;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.datastax.driver.core.Session;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.spring.boot.autoconfigure.cassandra.CassandraRelationshipDetectorTest.CassandraTestApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CassandraTestApplication.class)
public class CassandraRelationshipDetectorTest {

    @Autowired
    private CassandraRelationshipDetector detector;

    @Test
    public void cassandraRelationshipWithSessionAvailable() {
        Assertions.assertThat(detector.detect())
                .contains(Dependency.on(Component.of("keyspace", ComponentType.CASSANDRA)));
    }

    @SpringBootApplication
    static class CassandraTestApplication {

        @Bean
        public Session session() {
            Session s = Mockito.mock(Session.class);
            Mockito.when(s.getLoggedKeyspace()).thenReturn("keyspace");
            return s;
        }

    }

}

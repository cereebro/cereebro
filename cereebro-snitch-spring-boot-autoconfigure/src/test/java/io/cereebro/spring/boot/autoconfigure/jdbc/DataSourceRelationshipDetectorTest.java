package io.cereebro.spring.boot.autoconfigure.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.core.Component;
import io.cereebro.core.Dependency;
import io.cereebro.spring.boot.autoconfigure.jdbc.DataSourceRelationshipDetectorTest.DataSourceTestApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataSourceTestApplication.class)
public class DataSourceRelationshipDetectorTest {

    @Autowired
    private DataSourceRelationshipDetector detector;

    @Test
    public void dataSourceRelationshipWithDataSourceAvailable() throws SQLException {

        Assertions.assertThat(detector.detect()).contains(Dependency.on(Component.of("catalog", "DATABASE")));
    }

    @SpringBootApplication
    static class DataSourceTestApplication {

        @Bean
        public DataSource dataSource() throws Exception {
            DataSource ds = Mockito.mock(DataSource.class);
            Connection c = Mockito.mock(Connection.class);
            Mockito.when(ds.getConnection()).thenReturn(c);
            Mockito.when(c.getCatalog()).thenReturn("catalog");
            return ds;
        }

    }

}

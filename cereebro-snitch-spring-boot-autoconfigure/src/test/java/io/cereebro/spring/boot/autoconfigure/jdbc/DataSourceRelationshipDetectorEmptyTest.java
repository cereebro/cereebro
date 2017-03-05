package io.cereebro.spring.boot.autoconfigure.jdbc;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.spring.boot.autoconfigure.jdbc.DataSourceRelationshipDetectorEmptyTest.NoDataSourceTestApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NoDataSourceTestApplication.class)
public class DataSourceRelationshipDetectorEmptyTest {

    @Autowired
    private DataSourceRelationshipDetector detector;

    @Test
    public void dataSourceRelationshipWithoutDataSourceAvailable() {
        Assertions.assertThat(detector.detect()).isEmpty();
    }

    @SpringBootApplication
    static class NoDataSourceTestApplication {

    }

}

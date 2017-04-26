/*
 * Copyright © 2017 the original authors (http://cereebro.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.cereebro.spring.boot.autoconfigure.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.spring.boot.autoconfigure.jdbc.DataSourceRelationshipDetectorTest.DataSourceTestApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataSourceTestApplication.class)
public class DataSourceRelationshipDetectorTest {

    @Autowired
    private DataSourceRelationshipDetector detector;

    @Test
    public void dataSourceRelationshipWithDataSourceAvailable() throws SQLException {

        Assertions.assertThat(detector.detect())
                .contains(Dependency.on(Component.of("catalog", ComponentType.RELATIONAL_DATABASE)));
    }

    @SpringBootApplication(exclude = { MongoAutoConfiguration.class })
    static class DataSourceTestApplication {

        @Bean
        public DataSource dataSource() throws Exception {
            DataSource ds = Mockito.mock(DataSource.class);
            Connection c = Mockito.mock(Connection.class);
            DatabaseMetaData metadata = Mockito.mock(DatabaseMetaData.class);
            Mockito.when(ds.getConnection()).thenReturn(c);
            Mockito.when(c.getCatalog()).thenReturn("catalog");
            Mockito.when(c.getMetaData()).thenReturn(metadata);
            Mockito.when(metadata.getDatabaseProductName()).thenReturn("relational");
            return ds;
        }

    }

}

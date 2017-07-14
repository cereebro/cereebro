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
package io.cereebro.snitch.detect.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import javax.sql.DataSource;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.cereebro.core.Relationship;

/**
 * {@link DataSourceRelationshipDetector} unit tests.
 * 
 * @author michaeltecourt
 */
public class DataSourceRelationshipDetectorUnitTest {

    private DataSourceRelationshipDetector detector;
    private DataSource dataSourceMock;
    private Connection connectionMock;

    @Before
    public void setUp() throws SQLException {
        detector = new DataSourceRelationshipDetector(Collections.emptyList());
        dataSourceMock = Mockito.mock(DataSource.class);
        connectionMock = Mockito.mock(Connection.class);
        Mockito.when(dataSourceMock.getConnection()).thenReturn(connectionMock);
    }

    @Test
    public void extractNameShouldUseSchemaFirst() throws SQLException {
        Mockito.when(connectionMock.getSchema()).thenReturn("schema");
        Mockito.when(connectionMock.getCatalog()).thenReturn("catalog");
        String name = detector.extractName(connectionMock);
        Assertions.assertThat(name).isEqualTo("schema");
    }

    @Test
    public void extractNameWithoutSchemaShouldReturnCatalog() throws SQLException {
        Mockito.when(connectionMock.getSchema()).thenReturn(null);
        Mockito.when(connectionMock.getCatalog()).thenReturn("catalog");
        String name = detector.extractName(connectionMock);
        Assertions.assertThat(name).isEqualTo("catalog");
    }

    @Test
    public void extractNameShouldReturnDefaultName() throws SQLException {
        Mockito.when(connectionMock.getSchema()).thenReturn(null);
        Mockito.when(connectionMock.getCatalog()).thenReturn(null);
        String name = detector.extractName(connectionMock);
        Assertions.assertThat(name).isEqualTo("default_db");
    }

    @Test
    public void failureShouldReturnEmptySet() throws SQLException {
        DataSource dataSourceMock = Mockito.mock(DataSource.class);
        Mockito.when(dataSourceMock.getConnection()).thenThrow(Mockito.mock(SQLException.class));
        Set<Relationship> result = new DataSourceRelationshipDetector(Arrays.asList(dataSourceMock)).detect();
        Assertions.assertThat(result).isEmpty();
    }

}

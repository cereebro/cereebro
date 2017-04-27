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
package io.cereebro.snitch;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cereebro.core.ApplicationAnalyzer;
import io.cereebro.core.SystemFragment;
import io.cereebro.snitch.CereebroProperties;
import io.cereebro.snitch.FileWriterSnitch;

/**
 * {@link FileWriterSnitch} unit tests.
 * 
 * @author michaeltecourt
 */
public class FileWriterSnitchTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private ApplicationAnalyzer analyzerMock;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        analyzerMock = Mockito.mock(ApplicationAnalyzer.class);
        Mockito.when(analyzerMock.analyzeSystem()).thenReturn(SystemFragment.empty());
        objectMapper = new ObjectMapper();
    }

    @Test
    public void writeToDefinedLocation() throws Exception {
        CereebroProperties properties = new CereebroProperties();
        File temp = temporaryFolder.newFile();
        properties.getSnitch().getFile().setLocation(temp.getAbsolutePath());
        FileWriterSnitch snitch = new FileWriterSnitch(analyzerMock, objectMapper, properties);
        snitch.run();
        String result = new String(Files.readAllBytes(temp.toPath()));
        Assertions.assertThat(result).isEqualTo("{\"componentRelationships\":[]}");
    }

    @Test
    public void writeToRandomTemporaryFileWhenNoLocationDefined() throws Exception {
        CereebroProperties properties = new CereebroProperties();
        final String componentName = "cereebro-unit-tests";
        properties.getApplication().getComponent().setName(componentName);
        properties.getSnitch().getFile().setLocation(null);
        FileWriterSnitch snitch = new FileWriterSnitch(analyzerMock, objectMapper, properties);
        snitch.run();
        File tempRoot = new File(System.getProperty("java.io.tmpdir"));
        File[] tempFiles = tempRoot.listFiles((FilenameFilter) (dir, fileName) -> fileName.contains(componentName));
        Assertions.assertThat(tempFiles).isNotEmpty();
        Stream.of(tempFiles).forEach(f -> f.deleteOnExit());
    }

    @Test
    public void ioExceptionShouldBeSwallowed() throws Exception {
        CereebroProperties properties = new CereebroProperties();
        File temp = temporaryFolder.newFile();
        properties.getSnitch().getFile().setLocation(temp.getAbsolutePath());
        ObjectMapper messedUpMapperMock = Mockito.mock(ObjectMapper.class);
        Mockito.when(messedUpMapperMock.writeValueAsString(SystemFragment.empty()))
                .thenThrow(Mockito.mock(JsonProcessingException.class));
        FileWriterSnitch snitch = new FileWriterSnitch(analyzerMock, messedUpMapperMock, properties);
        snitch.run();
        Assertions.assertThat(temp.length()).isEqualTo(0);
    }

}

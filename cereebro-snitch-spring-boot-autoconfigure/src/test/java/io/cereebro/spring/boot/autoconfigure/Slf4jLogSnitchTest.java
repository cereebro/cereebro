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
package io.cereebro.spring.boot.autoconfigure;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Level;
import io.cereebro.core.ApplicationAnalyzer;
import io.cereebro.core.SystemFragment;

/**
 * {@link Slf4jLogSnitch} unit tests.
 * 
 * @author michaeltecourt
 */
public class Slf4jLogSnitchTest {

    ObjectMapper objectMapperMock;
    ApplicationAnalyzer analyzerMock;
    Slf4jLogSnitch logSnitch;

    @Before
    public void setUp() {
        objectMapperMock = Mockito.mock(ObjectMapper.class);
        analyzerMock = Mockito.mock(ApplicationAnalyzer.class);
        logSnitch = new Slf4jLogSnitch(analyzerMock, objectMapperMock);
    }

    @Test
    public void log() throws IOException {
        Level previousLogLevel = setLogLevel(Slf4jLogSnitch.class, Level.INFO);

        SystemFragment frag = SystemFragment.empty();
        Mockito.when(analyzerMock.analyzeSystem()).thenReturn(frag);
        Mockito.when(objectMapperMock.writeValueAsString(frag)).thenReturn("");
        logSnitch.log();
        Mockito.verify(analyzerMock).analyzeSystem();
        Mockito.verify(objectMapperMock).writeValueAsString(frag);

        setLogLevel(Slf4jLogSnitch.class, previousLogLevel);
    }

    @Test
    public void logInfoDisabledShouldNotLog() throws IOException {
        Level previousLogLevel = setLogLevel(Slf4jLogSnitch.class, Level.ERROR);

        logSnitch.log();
        Mockito.verify(analyzerMock, Mockito.never()).analyzeSystem();
        Mockito.verify(objectMapperMock, Mockito.never()).writeValueAsString(Mockito.any());

        setLogLevel(Slf4jLogSnitch.class, previousLogLevel);
    }

    /**
     * Changes dynamically the log level of a certain class, assuming Logback is
     * used.
     * 
     * @param clazz
     *            Class whose log level is to change.
     * @param newLevel
     *            Log Level wanted.
     * @return the previous log level.
     */
    private static Level setLogLevel(Class<?> clazz, Level newLevel) {
        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getILoggerFactory()
                .getLogger(clazz.getName());
        Level previousLevel = logger.getLevel();
        logger.setLevel(newLevel);
        return previousLevel;
    }

}

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

import java.io.IOException;
import java.util.Objects;

import org.springframework.boot.CommandLineRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cereebro.core.ApplicationAnalyzer;
import io.cereebro.core.SystemFragment;
import lombok.extern.slf4j.Slf4j;

/**
 * Writes Snitch data in the logs.
 * 
 * @author michaeltecourt
 */
@Slf4j
public class Slf4jSnitchLogger implements CommandLineRunner {

    private final ApplicationAnalyzer analyzer;
    private final ObjectMapper objectMapper;

    /**
     * Writes Snitch data in the logs.
     * 
     * @param analyzer
     *            An application analyzer that will provide information about
     *            the application and its relationships.
     * @param objectMapper
     *            JSON Object Mapper.
     */
    public Slf4jSnitchLogger(ApplicationAnalyzer analyzer, ObjectMapper objectMapper) {
        this.analyzer = Objects.requireNonNull(analyzer, "Application analyzer required");
        this.objectMapper = Objects.requireNonNull(objectMapper, "Object mapper required");
    }

    /**
     * Write the SystemFragment in the logs.
     */
    public void log() {
        if (LOGGER.isInfoEnabled()) {
            try {
                SystemFragment frag = analyzer.analyzeSystem();
                String fragString = objectMapper.writeValueAsString(frag);
                LOGGER.info("System fragment : {}", fragString);
            } catch (IOException | RuntimeException e) {
                // Swallow the exception, we don't want to prevent
                // the app from starting
                LOGGER.error("Error while logging system fragment", e);
            }
        }
    }

    @Override
    public void run(String... args) throws Exception {
        log();
    }

}

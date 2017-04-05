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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

import org.springframework.boot.CommandLineRunner;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cereebro.core.ApplicationAnalyzer;
import io.cereebro.core.SystemFragment;
import lombok.extern.slf4j.Slf4j;

/**
 * Writes the {@link SystemFragment} directly to a file on disk. If the user
 * does not define a location, the file will be written in a temporary folder
 * (mentioned in the logs).
 * 
 * @author michaeltecourt
 */
@Slf4j
public class FileWriterSnitch implements CommandLineRunner {

    private ApplicationAnalyzer analyzer;
    private ObjectMapper objectMapper;
    private CereebroProperties properties;

    public FileWriterSnitch(ApplicationAnalyzer analyzer, ObjectMapper objectMapper, CereebroProperties properties) {
        this.analyzer = Objects.requireNonNull(analyzer, "Application analyzer required");
        this.objectMapper = Objects.requireNonNull(objectMapper, "Object mapper required");
        this.properties = Objects.requireNonNull(properties, "File snitch properties required");
    }

    @Override
    public void run(String... args) throws Exception {
        String file = properties.getSnitch().getFile().getLocation();
        try {
            write(StringUtils.hasText(file) ? new File(file)
                    : File.createTempFile(properties.getApplication().getComponent().getName(), ".json"));
        } catch (IOException e) {
            // Swallow the exception, we don't want to prevent
            // the app from starting
            LOGGER.error("Error while writing JSON to file : " + file, e);
        }
    }

    /**
     * Write to a file the SystemFragment discovered after analyzing the
     * application.
     * 
     * @param file
     *            File to write.
     * @throws IOException
     *             if something wrong happens while serializing the
     *             SystemFragment or writing the file.
     */
    public void write(File file) throws IOException {
        SystemFragment frag = analyzer.analyzeSystem();
        String fragString = objectMapper.writeValueAsString(frag);
        LOGGER.info("Writing system fragment to file : {}", file);
        FileWriter writer = new FileWriter(file);
        writer.write(fragString);
        writer.close();
    }

}

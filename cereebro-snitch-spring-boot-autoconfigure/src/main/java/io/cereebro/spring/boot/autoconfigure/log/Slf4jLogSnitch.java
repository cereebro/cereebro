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
package io.cereebro.spring.boot.autoconfigure.log;

import java.io.IOException;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cereebro.core.Snitch;
import io.cereebro.core.SystemFragment;
import lombok.extern.slf4j.Slf4j;

/**
 * Writes Snitch data in the logs.
 * 
 * @author michaeltecourt
 */
@Slf4j
public class Slf4jLogSnitch {

    private final Snitch snitch;
    private final ObjectMapper objectMapper;

    /**
     * Writes Snitch data in the logs.
     * 
     * @param snitch
     * @param objectMapper
     */
    public Slf4jLogSnitch(Snitch snitch, ObjectMapper objectMapper) {
        this.snitch = Objects.requireNonNull(snitch, "Snitch required");
        this.objectMapper = Objects.requireNonNull(objectMapper, "Object mapper required");
    }

    /**
     * Write the SystemFragment in the logs.
     * 
     * @throws IOException
     *             when something wrong happens.
     */
    public void log() throws IOException {
        if (LOGGER.isInfoEnabled()) {
            SystemFragment frag = snitch.snitch();
            String fragString = objectMapper.writeValueAsString(frag);
            LOGGER.info("Snitch URI : {} - System fragment : {}", snitch.getUri(), fragString);
        }
    }

}

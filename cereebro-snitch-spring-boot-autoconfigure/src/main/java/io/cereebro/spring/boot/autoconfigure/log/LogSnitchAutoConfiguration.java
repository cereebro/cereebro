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

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cereebro.core.Snitch;
import io.cereebro.spring.boot.autoconfigure.actuate.CereebroWebMvcEndpointConfiguration;

/**
 * Logs the detected system fragment on application startup.
 * 
 * @author michaeltecourt
 */
@Configuration
@AutoConfigureAfter(CereebroWebMvcEndpointConfiguration.class)
@ConditionalOnProperty(prefix = "cereebro.snitch", name = "log-on-startup", havingValue = "true", matchIfMissing = false)
public class LogSnitchAutoConfiguration {

    @Autowired
    private Snitch snitch;

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void log() throws IOException {
        logSnitch().log();
    }

    @Bean
    public Slf4jLogSnitch logSnitch() {
        return new Slf4jLogSnitch(snitch, objectMapper);
    }

}

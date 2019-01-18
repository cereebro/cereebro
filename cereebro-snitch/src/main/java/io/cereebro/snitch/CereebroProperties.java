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

import java.util.UUID;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@ConfigurationProperties(prefix = "cereebro", ignoreUnknownFields = true)
@Data
@Slf4j
public final class CereebroProperties implements EnvironmentAware {

    private ComponentRelationshipsProperties application = new ComponentRelationshipsProperties();
    private SnitchProperties snitch = new SnitchProperties();

    @Override
    public void setEnvironment(Environment env) {
        if (!StringUtils.hasText(application.getComponent().getName())) {
            String appName = env.getProperty("spring.application.name");
            application.getComponent().setName(StringUtils.hasText(appName) ? appName : generateName());
        }
    }

    private String generateName() {
        LOGGER.warn("Generating random name for this application -- please set spring.application.name property !");
        return UUID.randomUUID().toString();
    }

}

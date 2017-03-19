/*
 * Copyright © 2009 the original authors (http://cereebro.io)
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

import java.util.UUID;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import lombok.Data;

@ConfigurationProperties(prefix = "cereebro", ignoreUnknownFields = true)
@Data
public final class CereebroProperties implements EnvironmentAware {

    private ComponentRelationshipsProperties application = new ComponentRelationshipsProperties();

    @Override
    public void setEnvironment(Environment env) {
        if (!StringUtils.hasText(application.getComponent().getName())) {
            // set the application name from the environment,
            // but allow the defaults to use relaxed binding
            // (shamelessly copied from Spring Boot)
            RelaxedPropertyResolver springPropertyResolver = new RelaxedPropertyResolver(env, "spring.application.");
            String appName = springPropertyResolver.getProperty("name");
            application.getComponent().setName(StringUtils.hasText(appName) ? appName : UUID.randomUUID().toString());
        }
    }

}

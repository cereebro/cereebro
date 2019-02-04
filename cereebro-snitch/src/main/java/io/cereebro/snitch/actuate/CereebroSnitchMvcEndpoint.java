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
package io.cereebro.snitch.actuate;

import java.net.URI;
import java.util.Objects;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.cereebro.core.ApplicationAnalyzer;
import io.cereebro.core.SnitchEndpoint;
import io.cereebro.core.SystemFragment;

/**
 * Snitch actuator Endpoint. Tells everything it knows about the host Spring
 * Boot application and its dependencies.
 * 
 * @author lucwarrot
 * @author michaeltecourt
 */
@ConfigurationProperties(prefix = "endpoints.cereebro")
// uri can be changed using management.endpoints.web.path-mapping.cereebro: /cereebro/snitch
// we can't use id 'cereebro/snitch' here because '/' is not allowed as id
@Endpoint(id = "cereebro") 
public class CereebroSnitchMvcEndpoint implements SnitchEndpoint {

    public static final String DEFAULT_PATH = "/cereebro/snitch";

    private ApplicationAnalyzer applicationAnalyzer;

    /**
     * Snitch actuator Endpoint. Tells everything it knows about the host Spring
     * Boot application and its dependencies.
     * 
     * @param analyzer
     *            An application analyzer that will provide information about
     *            the application and its relationships.
     */
    public CereebroSnitchMvcEndpoint(ApplicationAnalyzer analyzer) {
        this.applicationAnalyzer = Objects.requireNonNull(analyzer, "Application analyzer required");
    }

    @Override
    public URI getUri() {
        return URI.create(DEFAULT_PATH);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Override
    @ReadOperation
    public SystemFragment snitch() {
        return applicationAnalyzer.analyzeSystem();
    }

}

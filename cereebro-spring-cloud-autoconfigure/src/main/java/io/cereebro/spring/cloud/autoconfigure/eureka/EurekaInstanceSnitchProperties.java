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
package io.cereebro.spring.cloud.autoconfigure.eureka;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * Fine tune the Snitch URI declared in Eureka metadata with these properties.
 * <p>
 * Note that this class is not used as Spring Boot
 * {@link ConfigurationProperties} because relaxed binding is needed to resolve
 * management properties.
 * </p>
 * 
 * @author michaeltecourt
 */
@Data
final class EurekaInstanceSnitchProperties {

    /**
     * Absolute URL of the endpoint (ex: "http://localhost:8080/cereebro"). <br>
     * Will take precedence over the {@link #urlPath} if both are set.
     */
    private String url;

    /**
     * Relative path of the endpoint location (ex: "/cereebro").
     */
    private String urlPath;

}
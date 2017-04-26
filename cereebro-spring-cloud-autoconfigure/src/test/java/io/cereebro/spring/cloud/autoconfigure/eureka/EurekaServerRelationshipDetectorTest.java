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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

import com.netflix.discovery.EurekaClient;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;

/**
 * {@link EurekaServerRelationshipDetector} unit tests.
 * 
 * @author michaeltecourt
 */
public class EurekaServerRelationshipDetectorTest {

    @Test
    public void nullEurekaClientListShouldReturnEmptyRels() {
        EurekaServerRelationshipDetector detector = new EurekaServerRelationshipDetector(null);
        Assertions.assertThat(detector.detect()).isEmpty();
    }

    @Test
    public void emptyEurekaClientListShouldReturnEmptyRels() {
        EurekaServerRelationshipDetector detector = new EurekaServerRelationshipDetector(new ArrayList<>());
        Assertions.assertThat(detector.detect()).isEmpty();
    }

    @Test
    public void multipleEurekaClientsShouldReturnSingleDependencyOnEurekaServer() {
        EurekaClient eurekaClientMock = Mockito.mock(EurekaClient.class);
        EurekaServerRelationshipDetector detector = new EurekaServerRelationshipDetector(
                Arrays.asList(eurekaClientMock));
        Dependency dependency = Dependency.on(
                Component.of(EurekaServerRelationshipDetector.DEFAULT_NAME, ComponentType.HTTP_APPLICATION_REGISTRY));
        Set<Relationship> expected = new HashSet<>(Arrays.asList(dependency));
        Assertions.assertThat(detector.detect()).isEqualTo(expected);
    }

}

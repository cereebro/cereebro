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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import io.cereebro.core.Component;
import io.cereebro.core.Consumer;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import io.cereebro.spring.boot.autoconfigure.CereebroProperties;
import io.cereebro.spring.boot.autoconfigure.ComponentProperties;
import io.cereebro.spring.boot.autoconfigure.ComponentRelationshipsProperties;
import io.cereebro.spring.boot.autoconfigure.ConfigurationPropertiesRelationshipDetector;
import io.cereebro.spring.boot.autoconfigure.ConsumerProperties;
import io.cereebro.spring.boot.autoconfigure.DependencyProperties;

/**
 * {@link ConfigurationPropertiesRelationshipDetector} unit tests.
 * 
 * @author michaeltecourt
 */
public class ConfigurationPropertiesRelationshipDetectorTest {

    @Test
    public void detectRelationshipsBasedOnPropertiesShouldReturnDependenciesAndConsumers() {
        CereebroProperties properties = new CereebroProperties();
        ComponentRelationshipsProperties application = new ComponentRelationshipsProperties();
        DependencyProperties d = new DependencyProperties();
        ConsumerProperties c = new ConsumerProperties();
        ComponentProperties consumer = new ComponentProperties();
        consumer.setName("apocalypse");
        consumer.setType("villain");
        c.setComponent(consumer);
        application.setConsumers(Arrays.asList(c));
        ComponentProperties dependency = new ComponentProperties();
        dependency.setName("wolverine");
        dependency.setType("superhero");
        d.setComponent(dependency);
        application.setDependencies(Arrays.asList(d));
        properties.setApplication(application);

        Set<Relationship> expected = new HashSet<>(Arrays.asList(Dependency.on(Component.of("wolverine", "superhero")),
                Consumer.by(Component.of("apocalypse", "villain"))));
        ConfigurationPropertiesRelationshipDetector detector = new ConfigurationPropertiesRelationshipDetector(
                properties);
        Set<Relationship> actual = detector.detect();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void constructorWithNullArgumentShouldThrowNullPointerException() {
        new ConfigurationPropertiesRelationshipDetector(null);
    }

    @Test
    public void detectPropertiesWithoutRelationshipsShouldReturnEmptySet() {
        Set<Relationship> expected = new HashSet<>();
        ConfigurationPropertiesRelationshipDetector detector = new ConfigurationPropertiesRelationshipDetector(
                new CereebroProperties());
        Set<Relationship> actual = detector.detect();
        Assert.assertEquals(expected, actual);
    }

}

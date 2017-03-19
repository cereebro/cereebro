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
package io.cereebro.spring.boot.autoconfigure.actuate;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentRelationships;
import io.cereebro.core.Consumer;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import io.cereebro.core.RelationshipDetector;
import io.cereebro.core.SystemFragment;

/**
 * {@link CereebroSnitchMvcEndpoint} unit tests.
 * 
 * @author michaeltecourt
 */
public class CereebroSnitchMvcEndpointTest {

    private RelationshipDetector relationshipDetectorMock;
    private Component application;
    private CereebroSnitchMvcEndpoint endpoint;

    @Before
    public void setUp() {
        application = Component.of("gambit", "superhero");
        relationshipDetectorMock = Mockito.mock(RelationshipDetector.class);
        endpoint = new CereebroSnitchMvcEndpoint(application, relationshipDetectorMock);
    }

    @Test(expected = NullPointerException.class)
    public void constructorWithNullApplicationComponentShouldThrowNullPointerException() {
        new CereebroSnitchMvcEndpoint(null, relationshipDetectorMock);
    }

    @Test(expected = NullPointerException.class)
    public void constructorWithNullRelationshipDetectorShouldThrowNullPointerException() {
        new CereebroSnitchMvcEndpoint(application, null);
    }

    @Test
    public void isSensitive() {
        Assert.assertTrue(endpoint.isSensitive());
    }

    @Test
    public void isEnabled() {
        Assert.assertTrue(endpoint.isEnabled());
    }

    @Test
    public void location() {
        Assert.assertEquals(URI.create("/cereebro/snitch"), endpoint.getUri());
    }

    @Test
    public void path() {
        Assert.assertEquals("/cereebro/snitch", endpoint.getPath());
    }

    @Test
    public void snitch() {
        Dependency d1 = Dependency.on(Component.of("cards", "game"));
        Dependency d2 = Dependency.on(Component.of("rogue", "superhero"));
        Consumer consumer = Consumer.by(Component.of("angel", "superhero"));
        Set<Relationship> rels = new HashSet<>(Arrays.asList(d1, d2, consumer));
        Mockito.when(relationshipDetectorMock.detect()).thenReturn(rels);
        SystemFragment actual = endpoint.snitch();

        Set<Dependency> dependencies = new HashSet<>(Arrays.asList(d1, d2));
        Set<Consumer> consumers = new HashSet<>(Arrays.asList(consumer));
        ComponentRelationships r = ComponentRelationships.of(application, dependencies, consumers);
        SystemFragment expected = SystemFragment.of(r);

        Assert.assertEquals(expected, actual);
    }

}

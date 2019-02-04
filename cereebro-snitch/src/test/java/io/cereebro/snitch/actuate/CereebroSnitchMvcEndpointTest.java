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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import io.cereebro.core.ApplicationAnalyzer;
import io.cereebro.core.Component;
import io.cereebro.core.ComponentRelationships;
import io.cereebro.core.Consumer;
import io.cereebro.core.Dependency;
import io.cereebro.core.SystemFragment;

/**
 * {@link CereebroSnitchMvcEndpoint} unit tests.
 * 
 * @author michaeltecourt
 */
@RunWith(MockitoJUnitRunner.class)
public class CereebroSnitchMvcEndpointTest {

    @Mock
    private ApplicationAnalyzer analyzer;
    @InjectMocks
    private CereebroSnitchMvcEndpoint endpoint;

    @Before
    public void setUp() {
        endpoint = new CereebroSnitchMvcEndpoint(analyzer);
    }

    @Test(expected = NullPointerException.class)
    public void constructorWithNullAnalyzerShouldThrowNullPointerException() {
        new CereebroSnitchMvcEndpoint(null);
    }

    @Test
    public void snitch() {
        Dependency d1 = Dependency.on(Component.of("cards", "game"));
        Consumer c1 = Consumer.by(Component.of("angel", "superhero"));
        ComponentRelationships rels = ComponentRelationships.builder().component(Component.of("gambit", "xmen"))
                .addDependency(d1).addConsumer(c1).build();

        Mockito.when(analyzer.analyzeSystem()).thenReturn(SystemFragment.of(rels));
        SystemFragment actual = endpoint.snitch();

        Set<Dependency> dependencies = new HashSet<>(Arrays.asList(d1));
        Set<Consumer> consumers = new HashSet<>(Arrays.asList(c1));
        ComponentRelationships r = ComponentRelationships.of(Component.of("gambit", "xmen"), dependencies, consumers);
        SystemFragment expected = SystemFragment.of(r);

        Assert.assertEquals(expected, actual);
    }

}

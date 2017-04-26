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
package io.cereebro.core;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * {@link SimpleSystemResolver} unit tests.
 * 
 * @author michaeltecourt
 */
public class SimpleSystemResolverTest {

    private static final Component CABLE = Component.of("cable", "future");
    private static final Component SABRETOOTH = Component.of("sabretooth", "villain");
    private static final Component CYCLOP = Component.of("cyclop", "superhero");
    private static final Component XAVIER = Component.of("xavier", "superhero");
    private static final Component WOLVERINE = Component.of("wolverine", "superhero");
    private static final Component PHOENIX = Component.of("phoenix", "superhero");

    private static final String SYSTEM_NAME = "xmen";

    private SimpleSystemResolver resolver;

    @Before
    public void setUp() {
        resolver = new SimpleSystemResolver();
    }

    @Test
    public void resolvingEmptySnitchRegistryShouldReturnEmptySystem() {
        System actual = resolver.resolve(SYSTEM_NAME, StaticSnitchRegistry.of());
        Assertions.assertThat(actual).isEqualTo(System.empty(SYSTEM_NAME));
    }

    /**
     * This fat test name means that :
     * <ul>
     * <li>if B is a dependency of A, then A should be resolved as a consumer of
     * B.</li>
     * <li>Corollary : if C is a consumer of D, then D should be resolved as a
     * dependency of C</li>
     * </ul>
     */
    @Test
    public void resolvingComponentRelationshipsShouldCreateInvertedRelationshipOnRelatedComponents() {
        // Initial relationships
        // @formatter:off
        ComponentRelationships wolverineRels = ComponentRelationships.builder()
                .component(WOLVERINE)
                .addDependency(Dependency.on(PHOENIX))
                .addConsumer(Consumer.by(SABRETOOTH))
                .build();
        // @formatter:on
        Snitch snitch1 = StaticSnitch.of(URI.create("fake://1"), SystemFragment.of(wolverineRels));
        System actual = resolver.resolve(SYSTEM_NAME, StaticSnitchRegistry.of(snitch1));

        // Resolved relationships (expected)
        // @formatter:off
        ComponentRelationships wolverineResolved = ComponentRelationships.builder()
                .component(WOLVERINE)
                .addDependency(Dependency.on(PHOENIX))
                .addConsumer(Consumer.by(SABRETOOTH))
                .build();
        
        ComponentRelationships phoenixResolved = ComponentRelationships.builder()
                .component(PHOENIX)
                .addConsumer(Consumer.by(WOLVERINE))
                .build();

        ComponentRelationships sabretoothResolved = ComponentRelationships.builder()
                .component(SABRETOOTH)
                .addDependency(Dependency.on(WOLVERINE))
                .build();
        // @formatter:on
        System expected = System.of(SYSTEM_NAME, wolverineResolved, phoenixResolved, sabretoothResolved);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void resolveBidirectionalRelationship() {
        // Initial relationships
        // @formatter:off
        ComponentRelationships cyclopRels = ComponentRelationships.builder()
                .component(CYCLOP)
                .addDependency(Dependency.on(PHOENIX))
                .addDependency(Dependency.on(XAVIER))
                .build();
        
        ComponentRelationships phoenixRels = ComponentRelationships.builder()
                .component(PHOENIX)
                .addDependency(Dependency.on(CYCLOP))
                .addDependency(Dependency.on(XAVIER))
                .build();
        // @formatter:on
        Snitch snitch1 = StaticSnitch.of(URI.create("fake://1"), SystemFragment.of(cyclopRels));
        Snitch snitch2 = StaticSnitch.of(URI.create("fake://2"), SystemFragment.of(phoenixRels));
        SnitchRegistry registry = StaticSnitchRegistry.of(snitch1, snitch2);
        System actual = resolver.resolve(SYSTEM_NAME, registry);

        // Resolved relationships (expected)
        // @formatter:off
        ComponentRelationships cyclopResolved = ComponentRelationships.builder()
                .component(CYCLOP)
                .addDependency(Dependency.on(PHOENIX))
                .addDependency(Dependency.on(XAVIER))
                .addConsumer(Consumer.by(PHOENIX))
                .build();
        
        ComponentRelationships phoenixResolved = ComponentRelationships.builder()
                .component(PHOENIX)
                .addDependency(Dependency.on(CYCLOP))
                .addDependency(Dependency.on(XAVIER))
                .addConsumer(Consumer.by(CYCLOP))
                .build();
        
        ComponentRelationships xavierResolved = ComponentRelationships.builder()
                .component(XAVIER)
                .addConsumer(Consumer.by(CYCLOP))
                .addConsumer(Consumer.by(PHOENIX))
                .build();
        // @formatter:on
        System expected = System.of(SYSTEM_NAME, cyclopResolved, phoenixResolved, xavierResolved);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    /**
     * A component consuming itself should be resolved as both a dependency and
     * a consumer.
     */
    @Test
    public void resolveSelfRelationship() {
        // @formatter:off
        // Initial relationships
        ComponentRelationships cable = ComponentRelationships.builder()
                .component(CABLE)
                .addConsumer(Consumer.by(CABLE))
                .build();
        
        // Resolved relationships (expected)
        ComponentRelationships cableResolved = ComponentRelationships.builder()
                .component(CABLE)
                .addConsumer(Consumer.by(CABLE))
                .addDependency(Dependency.on(CABLE))
                .build();
        // @formatter:on
        Snitch snitch1 = StaticSnitch.of(URI.create("fake://1"), SystemFragment.of(cable));
        System actual = resolver.resolve(SYSTEM_NAME, StaticSnitchRegistry.of(snitch1));
        System expected = System.of(SYSTEM_NAME, cableResolved);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void snitchingExceptionShouldBeTranslatedToError() {
        Snitch snitchMock = Mockito.mock(Snitch.class);
        URI uri = URI.create("http://nope");
        String message = "nope";
        Mockito.when(snitchMock.getUri()).thenReturn(uri);
        SnitchingException snitchingExceptionMock = Mockito.mock(SnitchingException.class);
        Mockito.when(snitchingExceptionMock.getMessage()).thenReturn(message);
        Mockito.when(snitchingExceptionMock.getSnitchUri()).thenReturn(uri);
        Mockito.when(snitchMock.snitch()).thenThrow(snitchingExceptionMock);
        System actual = resolver.resolve(SYSTEM_NAME, StaticSnitchRegistry.of(snitchMock));
        ResolutionError error = ResolutionError.of(uri, message);
        System expected = System.of(SYSTEM_NAME, new HashSet<>(), new HashSet<>(Arrays.asList(error)));
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void runtimeExceptionShouldBeTranslatedToError() {
        Snitch snitchMock = Mockito.mock(Snitch.class);
        URI uri = URI.create("http://nope");
        String message = "nope";
        Mockito.when(snitchMock.getUri()).thenReturn(uri);
        RuntimeException exceptionMock = Mockito.mock(RuntimeException.class);
        Mockito.when(exceptionMock.getMessage()).thenReturn(message);
        Mockito.when(snitchMock.snitch()).thenThrow(exceptionMock);
        System actual = resolver.resolve(SYSTEM_NAME, StaticSnitchRegistry.of(snitchMock));
        ResolutionError error = ResolutionError.of(uri, "Could not access or process Snitch");
        System expected = System.of(SYSTEM_NAME, new HashSet<>(), new HashSet<>(Arrays.asList(error)));
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void resolutionUsingFragmentsShouldNotReturnErrors() {
        System system = resolver.resolve(SYSTEM_NAME, new HashSet<>());
        Assertions.assertThat(system).isEqualTo(System.empty(SYSTEM_NAME));
    }

}

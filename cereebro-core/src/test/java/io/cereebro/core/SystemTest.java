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
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * {@link System} unit tests.
 */
public class SystemTest {

    private static final String NAME = "name";

    @Test(expected = NullPointerException.class)
    public void constructorWithNullNameShouldThrowNullPointerException() {
        ComponentRelationships c = ComponentRelationships.of(TestHelper.componentA(),
                TestHelper.relationshipSetOfDependencyBAndConsumerC());
        new System(null, new HashSet<>(Arrays.asList(c)));
    }

    @Test(expected = NullPointerException.class)
    public void constructorWithNullRelsShouldThrowNullPointerException() {
        new System(NAME, null);
    }

    @Test
    public void constructor() {
        ComponentRelationships c = ComponentRelationships.of(TestHelper.componentA(),
                TestHelper.relationshipSetOfDependencyBAndConsumerC());
        Set<ComponentRelationships> rels = new HashSet<>(Arrays.asList(c));
        System system = System.of("system", rels);
        Assertions.assertThat(system.getName()).isEqualTo("system");
        Assertions.assertThat(system.getComponentRelationships()).isEqualTo(rels);
    }

    @Test
    public void verifyHashcodeEquals() {
        EqualsVerifier.forClass(System.class).usingGetClass().verify();
    }

    @Test
    public void testToString() {
        String toString = System.empty("xmen").toString();
        Assertions.assertThat(toString).contains("xmen");
    }

    @Test
    public void builderTest() {
        System actual = System.builder().name(NAME).componentRelationships(new HashSet<>()).errors(new HashSet<>())
                .build();
        Assertions.assertThat(System.empty(NAME)).isEqualTo(actual);
    }

    @Test
    public void hasErrorsShouldBeFalseForEmptySystem() {
        Assertions.assertThat(System.empty("xmen").hasErrors()).isFalse();
    }

    @Test
    public void hasErrorsShouldBeTrueWhenSystemContainsErrors() {
        ResolutionError error = ResolutionError.of(URI.create("/nope"), "message");
        System system = System.builder().name(NAME).componentRelationships(new HashSet<>())
                .errors(new HashSet<>(Arrays.asList(error))).build();
        Assertions.assertThat(system.hasErrors()).isTrue();
    }

}

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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * {@link Dependency} unit test.
 * 
 * @author michaeltecourt
 */
public class DependencyTest {

    @Test
    public void verifyHashcodeEquals() {
        EqualsVerifier.forClass(Dependency.class).usingGetClass().verify();
    }

    @Test
    public void testToString() {
        String toString = Dependency.on(Component.of("storm", "xmen")).toString();
        Assert.assertTrue(toString.contains("storm"));
        Assert.assertTrue(toString.contains("xmen"));
    }

    @Test
    public void constructor() {
        Component b = TestHelper.componentB();
        Dependency dependency = new Dependency(b);
        Assert.assertEquals(b, dependency.getComponent());
    }

    @Test(expected = NullPointerException.class)
    public void constructorWithNullArgumentShouldThrowNullPointerException() {
        new Dependency(null);
    }

    @Test
    public void staticFactoryMethodShouldReturnSameAsConstructor() {
        Component b = TestHelper.componentB();
        Dependency expected = new Dependency(b);
        Dependency actual = Dependency.on(b);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void asRelationshipSet() {
        Dependency dependency = Dependency.on(Component.of("x", "y"));
        Set<Relationship> rels = dependency.asRelationshipSet();
        Assertions.assertThat(rels).isEqualTo(new HashSet<>(Arrays.asList(dependency)));
    }
}

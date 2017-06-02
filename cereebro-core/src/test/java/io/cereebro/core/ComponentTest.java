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

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * {@link Component} unit tests.
 * 
 * @author michaeltecourt
 */
public class ComponentTest {

    @Test
    public void constructor() {
        Component c = Component.of("name", "type");
        Assert.assertEquals("name", c.getName());
        Assert.assertEquals("type", c.getType());
    }

    @Test(expected = NullPointerException.class)
    public void constructorWithNullNameShouldThrowNullPointerException() {
        Component.of(null, "type");
    }

    @Test(expected = NullPointerException.class)
    public void constructorWithNullTypeShouldThrowNullPointerException() {
        Component.of("name", null);
    }

    @Test
    public void verifyHashcodeEquals() {
        EqualsVerifier.forClass(Component.class).usingGetClass().verify();
    }

    @Test
    public void testToString() {
        String toString = Component.of("cyclop", "superhero").toString();
        java.lang.System.out.println(toString);
        Assert.assertTrue(toString.contains("cyclop"));
        Assert.assertTrue(toString.contains("superhero"));
    }

    @Test
    public void testAsString() {
        Assert.assertEquals("type:name", Component.of("name", "type").asString());
    }

    @Test
    public void equalsShouldIgnoreNameCase() {
        Assertions.assertThat(Component.of("C", "c")).isEqualTo(Component.of("c", "c"));
    }

}

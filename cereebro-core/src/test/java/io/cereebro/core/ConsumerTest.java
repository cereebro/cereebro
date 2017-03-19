/*
 * Copyright © 2009 the original authors (http://cereebro.io)
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

import org.junit.Assert;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * {@link Consumer} unit tests.
 * 
 * @author michaeltecourt
 */
public class ConsumerTest {

    @Test
    public void hashcodeEquals() {
        EqualsVerifier.forClass(Consumer.class).usingGetClass().verify();
    }

    @Test
    public void testToString() {
        String toString = Consumer.by(Component.of("wolverine", "xmen")).toString();
        Assert.assertTrue(toString.contains("wolverine"));
        Assert.assertTrue(toString.contains("xmen"));
    }

    @Test
    public void constructor() {
        Component c = TestHelper.componentC();
        Assert.assertEquals(c, new Consumer(c).getComponent());
    }

    @Test(expected = NullPointerException.class)
    public void constructorWithNullArgumentShouldThrowNullPointerException() {
        new Consumer(null);
    }

    @Test
    public void staticFactoryMethodShouldReturnSameResultAsConstructor() {
        Component a = TestHelper.componentA();
        Consumer expected = new Consumer(a);
        Consumer actual = Consumer.by(a);
        Assert.assertEquals(expected, actual);
    }

}

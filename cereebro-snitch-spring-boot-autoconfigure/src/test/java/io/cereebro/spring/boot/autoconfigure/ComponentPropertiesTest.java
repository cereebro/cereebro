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
package io.cereebro.spring.boot.autoconfigure;

import org.junit.Assert;
import org.junit.Test;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * {@link ComponentProperties} unit tests.
 * 
 * @author michaeltecourt
 *
 */
public class ComponentPropertiesTest {

    @Test
    public void equalsHashcode() {
        EqualsVerifier.forClass(ComponentProperties.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void defaultValues() {
        ComponentProperties c = new ComponentProperties();
        Assert.assertNull(c.getName());
        Assert.assertEquals(ComponentType.HTTP_APPLICATION, c.getType());
    }

    @Test
    public void toComponent() {
        ComponentProperties c = new ComponentProperties();
        c.setName("wolverine");
        c.setType("superhero");
        Component expected = Component.of("wolverine", "superhero");
        Component actual = c.toComponent();
        Assert.assertEquals(expected, actual);
    }

}

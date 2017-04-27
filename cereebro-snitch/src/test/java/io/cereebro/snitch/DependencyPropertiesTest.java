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
package io.cereebro.snitch;

import org.junit.Assert;
import org.junit.Test;

import io.cereebro.core.Component;
import io.cereebro.core.Dependency;
import io.cereebro.snitch.ComponentProperties;
import io.cereebro.snitch.DependencyProperties;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * {@link DependencyProperties} unit tests.
 * 
 * @author michaeltecourt
 */
public class DependencyPropertiesTest {

    @Test
    public void equalsHashcode() {
        EqualsVerifier.forClass(DependencyProperties.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void toDependency() {
        DependencyProperties c = new DependencyProperties();
        ComponentProperties cmp = new ComponentProperties();
        c.setComponent(cmp);
        cmp.setName("magneto");
        cmp.setType("villain");
        Assert.assertEquals(Dependency.on(Component.of("magneto", "villain")), c.toDependency());
    }

}

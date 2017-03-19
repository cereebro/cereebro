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
import io.cereebro.core.Consumer;
import io.cereebro.spring.boot.autoconfigure.ComponentProperties;
import io.cereebro.spring.boot.autoconfigure.ConsumerProperties;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * {@link ConsumerProperties} unit tests.
 * 
 * @author michaeltecourt
 */
public class ConsumerPropertiesTest {

    @Test
    public void equalsHashcode() {
        EqualsVerifier.forClass(ConsumerProperties.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void toConsumerModel() {
        ConsumerProperties c = new ConsumerProperties();
        ComponentProperties cmp = new ComponentProperties();
        c.setComponent(cmp);
        cmp.setName("gambit");
        cmp.setType("superhero");
        Assert.assertEquals(Consumer.by(Component.of("gambit", "superhero")), c.toConsumer());
    }

}

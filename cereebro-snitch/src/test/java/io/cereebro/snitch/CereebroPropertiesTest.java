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
import org.springframework.mock.env.MockEnvironment;

import io.cereebro.snitch.CereebroProperties;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * {@link CereebroProperties} unit tests.
 * 
 * @author michaeltecourt
 */
public class CereebroPropertiesTest {

    @Test
    public void equalsHashcode() {
        EqualsVerifier.forClass(CereebroProperties.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void setEnvironmentWithSpringApplicationNameShouldSetComponentName() {
        CereebroProperties c = new CereebroProperties();
        MockEnvironment env = new MockEnvironment();
        env.setProperty("spring.application.name", "cyclop");
        c.setEnvironment(env);
        Assert.assertEquals("cyclop", c.getApplication().getComponent().getName());
    }

    @Test
    public void setEnvironmentWithoutSpringApplicationNameShouldntChangeComponentName() {
        CereebroProperties c = new CereebroProperties();
        c.getApplication().getComponent().setName("storm");
        c.setEnvironment(new MockEnvironment());
        Assert.assertEquals("storm", c.getApplication().getComponent().getName());
    }

}

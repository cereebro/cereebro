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
package io.cereebro.server;

import java.util.Arrays;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * {@link CereebroServerProperties} unit tests.
 * 
 * @author michaeltecourt
 */
public class CereebroServerPropertiesTest {

    @Test
    public void verifyEqualsAndHashcode() {
        EqualsVerifier.forClass(CereebroServerProperties.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void testToString() {
        CereebroServerProperties server = new CereebroServerProperties();
        SystemProperties system = new SystemProperties();
        system.setName("alpha");
        system.setPath("/nope");
        SnitchProperties snitch = new SnitchProperties();
        snitch.setResources(Arrays.asList());
        system.setSnitch(snitch);
        server.setSystem(system);
        Assertions.assertThat(server.toString()).isNotEmpty();
    }

}

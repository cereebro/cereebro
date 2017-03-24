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
 * {@link SnitchProperties} unit tests.
 * 
 * @author michaeltecourt
 */
public class SnitchPropertiesTest {

    @Test
    public void verifyHashcodeAndEquals() {
        EqualsVerifier.forClass(SnitchProperties.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void callToString() {
        SnitchProperties props = new SnitchProperties();
        props.setResources(Arrays.asList());
        Assertions.assertThat(props.toString()).isNotEmpty();
    }

}

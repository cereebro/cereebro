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

import org.assertj.core.api.Assertions;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * {@link SystemProperties} unit tests.
 * 
 * @author michaeltecourt
 */
public class SystemPropertiesTest {

    @Test
    public void verifyHashcodeAndEquals() {
        EqualsVerifier.forClass(SystemProperties.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void callToString() {
        SystemProperties props = new SystemProperties();
        props.setName("");
        props.setPath("");
        props.setSnitch(new SnitchProperties());
        Assertions.assertThat(props.toString()).isNotEmpty();
    }

}

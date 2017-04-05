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
package io.cereebro.spring.boot.autoconfigure;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * {@link FileSnitchProperties} unit tests.
 * 
 * @author michaeltecourt
 *
 */
public class FileSnitchPropertiesTest {

    @Test
    public void verifyHashcodeAndEquals() {
        EqualsVerifier.forClass(FileSnitchProperties.class).suppress(Warning.NONFINAL_FIELDS).verify();
    }

    @Test
    public void shouldBeDisabledByDefault() {
        Assertions.assertThat(new FileSnitchProperties().isEnabled()).isFalse();
    }

    @Test
    public void testToString() {
        FileSnitchProperties props = new FileSnitchProperties();
        props.setEnabled(true);
        props.setLocation("/whatever");
        Assertions.assertThat(props.toString()).contains("whatever");
    }
}

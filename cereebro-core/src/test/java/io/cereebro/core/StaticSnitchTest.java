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

import java.net.URI;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class StaticSnitchTest {

    private static final URI SNITCH_URI = URI.create("fake://nope");
    private Snitch snitch;

    @Before
    public void setUp() {
        snitch = StaticSnitch.of(SNITCH_URI);
    }

    @Test
    public void getLocation() {
        Assert.assertEquals(SNITCH_URI, snitch.getUri());
    }

    @Test
    public void snitch() {
        SystemFragment actual = snitch.snitch();
        Assert.assertEquals(SystemFragment.empty(), actual);
    }

    @Test
    public void verifyHashcodeEquals() {
        EqualsVerifier.forClass(StaticSnitch.class).usingGetClass().verify();
    }

    @Test
    public void testToString() {
        Assert.assertTrue(snitch.toString().contains(SNITCH_URI.toString()));
    }

}

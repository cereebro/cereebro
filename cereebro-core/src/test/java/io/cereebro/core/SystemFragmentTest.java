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

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * {@link SystemFragment} unit tests.
 * 
 * @author michaeltecourt
 */
public class SystemFragmentTest {

    @Test
    public void constructor() {
        ComponentRelationships c = ComponentRelationships.of(TestHelper.componentA(),
                TestHelper.relationshipSetOfDependencyBAndConsumerC());
        HashSet<ComponentRelationships> rels = new HashSet<>(Arrays.asList(c));
        SystemFragment frag = SystemFragment.of(c);
        Assert.assertEquals(rels, frag.getComponentRelationships());
    }

    @Test
    public void verifyHashcodeEquals() {
        EqualsVerifier.forClass(SystemFragment.class).usingGetClass().verify();
    }

    @Test
    public void testToString() {
        // I'm a lazy slob
        Assert.assertNotNull(SystemFragment.empty().toString());
    }

}

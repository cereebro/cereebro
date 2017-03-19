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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link FixedRelationshipDetector} unit tests.
 * 
 * @author michaeltecourt
 */
public class FixedRelationshipDetectorTest {

    @Test
    public void constructorShouldCopyCollection() {
        Dependency a = Dependency.on(TestHelper.componentA());
        // Create a fixed detector based on only one relationship (a)
        List<Relationship> originalCollection = new ArrayList<>();
        originalCollection.add(a);
        FixedRelationshipDetector detector = new FixedRelationshipDetector(originalCollection);
        // Modify the original collection to add another relationship (b)
        Dependency b = Dependency.on(TestHelper.componentB());
        originalCollection.add(b);
        Set<Relationship> actual = detector.detect();
        // The expected result contains only the first relationship (a)
        Set<Relationship> expected = new HashSet<>();
        expected.add(a);
        Assert.assertEquals(expected, actual);
    }

}

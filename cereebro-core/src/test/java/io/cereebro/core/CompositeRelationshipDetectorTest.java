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

import java.util.Arrays;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link CompositeRelationshipDetector} unit tests.
 * 
 * @author michaeltecourt
 */
public class CompositeRelationshipDetectorTest {

    @Test
    public void detectShouldContainRelationshipDetectedByBothSources() {
        FixedRelationshipDetector detector1 = new FixedRelationshipDetector(Arrays.asList(TestHelper.dependencyOnB()));
        FixedRelationshipDetector detector2 = new FixedRelationshipDetector(
                TestHelper.relationshipSetOfDependencyBAndConsumerC());
        CompositeRelationshipDetector compositeDetector = new CompositeRelationshipDetector(
                Arrays.asList(detector1, detector2));
        Set<Relationship> expected = TestHelper.relationshipSetOfDependencyBAndConsumerC();
        Assert.assertEquals(expected, compositeDetector.detect());
    }

    @Test(expected = NullPointerException.class)
    public void constructorWithNullArgumentShouldThrownNullPointerException() {
        new CompositeRelationshipDetector(null);
    }

}

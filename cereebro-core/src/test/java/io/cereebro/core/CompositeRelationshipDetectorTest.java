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

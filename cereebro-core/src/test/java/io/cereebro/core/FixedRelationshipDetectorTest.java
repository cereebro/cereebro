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

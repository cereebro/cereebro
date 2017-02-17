package io.cereebro.core;

import java.net.URI;
import java.util.Objects;

import lombok.ToString;

/**
 * Fake Snitch with only a location.
 * <p>
 * Useful for mostly tests.
 * </p>
 * 
 * @author michaeltecourt
 */
@ToString
public class FakeSnitch implements Snitch {

    private final URI location;

    /**
     * Fake Snitch with only a location.
     * 
     * @param location
     */
    public FakeSnitch(URI location) {
        this.location = Objects.requireNonNull(location, "Snitch location required");
    }

    /**
     * Fake Snitch with only a location.
     * 
     * @param location
     * @return a FakeSnitch instance.
     */
    public static Snitch of(URI location) {
        return new FakeSnitch(location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), location);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !getClass().equals(obj.getClass())) {
            return false;
        }
        FakeSnitch that = (FakeSnitch) obj;
        return Objects.equals(this.location, that.location);
    }

    @Override
    public URI getLocation() {
        return location;
    }

    @Override
    public SystemFragment snitch() {
        return SystemFragment.empty();
    }

}

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
    private final SystemFragment systemFragment;

    /**
     * Fake Snitch.
     * 
     * @param location
     */
    public FakeSnitch(URI location) {
        this(location, SystemFragment.empty());
    }

    /**
     * Fake Snitch with only a location.
     * 
     * @param location
     * @param systemFragment
     */
    public FakeSnitch(URI location, SystemFragment systemFragment) {
        this.location = Objects.requireNonNull(location, "Snitch location required");
        this.systemFragment = Objects.requireNonNull(systemFragment, "System fragment required");
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

    /**
     * Fake Snitch.
     * 
     * @param location
     * @param systemFragment
     * @return a FakeSnitch instance.
     */
    public static Snitch of(URI location, SystemFragment systemFragment) {
        return new FakeSnitch(location, systemFragment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), location, systemFragment);
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
        return Objects.equals(this.location, that.location) && Objects.equals(this.systemFragment, that.systemFragment);
    }

    @Override
    public URI getLocation() {
        return location;
    }

    @Override
    public SystemFragment snitch() {
        return systemFragment;
    }

}

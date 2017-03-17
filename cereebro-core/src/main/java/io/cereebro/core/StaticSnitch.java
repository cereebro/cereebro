package io.cereebro.core;

import java.net.URI;
import java.util.Objects;

import lombok.ToString;

/**
 * Snitch that returns canned data.
 * 
 * @author michaeltecourt
 */
@ToString
public class StaticSnitch implements Snitch {

    private final URI uri;
    private final SystemFragment systemFragment;

    /**
     * Fake Snitch.
     * 
     * @param uri
     */
    public StaticSnitch(URI uri) {
        this(uri, SystemFragment.empty());
    }

    /**
     * Fake Snitch with only a uri.
     * 
     * @param uri
     * @param systemFragment
     */
    public StaticSnitch(URI location, SystemFragment systemFragment) {
        this.uri = Objects.requireNonNull(location, "Snitch uri required");
        this.systemFragment = Objects.requireNonNull(systemFragment, "System fragment required");
    }

    /**
     * Fake Snitch with only a uri.
     * 
     * @param uri
     * @return a FakeSnitch instance.
     */
    public static Snitch of(URI location) {
        return new StaticSnitch(location);
    }

    /**
     * Fake Snitch.
     * 
     * @param uri
     * @param systemFragment
     * @return a FakeSnitch instance.
     */
    public static Snitch of(URI location, SystemFragment systemFragment) {
        return new StaticSnitch(location, systemFragment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), uri, systemFragment);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !getClass().equals(obj.getClass())) {
            return false;
        }
        StaticSnitch that = (StaticSnitch) obj;
        return Objects.equals(this.uri, that.uri) && Objects.equals(this.systemFragment, that.systemFragment);
    }

    @Override
    public URI getUri() {
        return uri;
    }

    @Override
    public SystemFragment snitch() {
        return systemFragment;
    }

}

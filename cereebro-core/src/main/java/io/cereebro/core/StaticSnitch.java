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
     * Creates an Empty Snitch.
     * 
     * @param uri
     *            Snitch URI.
     */
    public StaticSnitch(URI uri) {
        this(uri, SystemFragment.empty());
    }

    /**
     * Snitch holding a static system fragment.
     * 
     * @param uri
     *            Snitch URI.
     * @param systemFragment
     *            static system fragment.
     */
    public StaticSnitch(URI uri, SystemFragment systemFragment) {
        this.uri = Objects.requireNonNull(uri, "Snitch uri required");
        this.systemFragment = Objects.requireNonNull(systemFragment, "System fragment required");
    }

    /**
     * Creates an Empty Snitch.
     * 
     * @param uri
     *            Snitch URI.
     * @return a StaticSnitch instance.
     */
    public static Snitch of(URI uri) {
        return new StaticSnitch(uri);
    }

    /**
     * Snitch holding a static system fragment.
     * 
     * @param uri
     *            Snitch URI.
     * @param systemFragment
     *            static system fragment.
     * @return a StaticSnitch instance.
     */
    public static Snitch of(URI uri, SystemFragment systemFragment) {
        return new StaticSnitch(uri, systemFragment);
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

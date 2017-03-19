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

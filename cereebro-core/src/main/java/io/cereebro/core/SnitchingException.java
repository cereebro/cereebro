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

public class SnitchingException extends RuntimeException {

    private static final long serialVersionUID = 2986000013370000092L;

    /**
     * URI of the Snitch that caused problems.
     */
    private final URI snitchUri;

    public SnitchingException(URI snitch, String message, Throwable cause) {
        super(message, cause);
        this.snitchUri = Objects.requireNonNull(snitch, "Snitch URI required");
    }

    public SnitchingException(URI snitch, String message) {
        this(snitch, message, null);
    }

    public SnitchingException(URI snitch, Throwable cause) {
        this(snitch, null, cause);
    }

    /**
     * URI of the Snitch that caused problems.
     * 
     * @return URI
     */
    public URI getSnitchUri() {
        return snitchUri;
    }

}

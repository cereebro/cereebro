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
    public URI getSnitchLocation() {
        return snitchUri;
    }

}

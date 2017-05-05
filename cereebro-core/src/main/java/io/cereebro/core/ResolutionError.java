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

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Error caught while resolving a System.
 * 
 * @author michaeltecourt
 */
@Slf4j
@ToString(doNotUseGetters = true)
public class ResolutionError {

    private final URI snitchUri;
    private final String message;
    @JsonIgnore
    private final Throwable cause;

    /**
     * Error caught while resolving a System.
     * 
     * @param snitchUri
     *            Snitch URI.
     * @param message
     *            Error message.
     * @param cause
     *            Cause of the error. Will not be serialized.
     */
    public ResolutionError(URI snitchUri, String message, Throwable cause) {
        this.snitchUri = Objects.requireNonNull(snitchUri, "Snitch URI required");
        this.message = Objects.requireNonNull(message, "Error message required");
        this.cause = cause;
    }

    /**
     * Error caught while resolving a System.
     * 
     * @param e
     *            Exception caught while reading Snitch data.
     * @return ResolutionError using the Snitch URI.
     */
    public static ResolutionError translate(SnitchingException e) {
        return new ResolutionError(e.getSnitchUri(), e.getMessage(), e);
    }

    /**
     * Error caught while resolving a System.
     * 
     * @param snitchUri
     *            Snitch URI.
     * @param message
     *            Error message.
     * @param cause
     *            Cause of the error. Will not be serialized.
     * @return ResolutionError
     */
    public static ResolutionError of(URI snitchUri, String message, Throwable cause) {
        return new ResolutionError(snitchUri, message, cause);
    }

    /**
     * Error caught while resolving a System.
     * 
     * @param snitchUri
     *            Snitch URI.
     * @param message
     *            Error message.
     * @return ResolutionError
     */
    public static ResolutionError of(URI snitchUri, String message) {
        return new ResolutionError(snitchUri, message, null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), message, snitchUri);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || !getClass().equals(obj.getClass())) {
            return false;
        }
        ResolutionError that = (ResolutionError) obj;
        return Objects.equals(this.message, that.message) && Objects.equals(this.snitchUri, that.snitchUri);
    }

    /**
     * @return Error message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return Snitch URI.
     */
    public URI getSnitchUri() {
        return snitchUri;
    }

    /**
     * Tells whether this error has a defined cause.
     * 
     * @return {@code true} if this error has a cause defined, {@code false}
     *         otherwise.
     */
    public boolean hasCause() {
        return getCause().isPresent();
    }

    /**
     * @return Error cause.
     */
    @JsonIgnore
    public Optional<Throwable> getCause() {
        return Optional.ofNullable(cause);
    }

    /**
     * Get the cause StackTrace as a String.
     * 
     * @return StackTrace string.
     */
    public Optional<String> getCauseStackTraceString() {
        Optional<Throwable> errorCause = getCause();
        if (errorCause.isPresent()) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            errorCause.get().printStackTrace(pw);
            String stackTraceString = sw.toString();
            closeQuietly(sw, pw);
            return Optional.of(stackTraceString);
        }
        return Optional.empty();
    }

    static void closeQuietly(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            try {
                closeable.close();
            } catch (IOException e) {
                LOGGER.error("Error while closing writer after printing StackTrace", e);
            }
        }
    }

}

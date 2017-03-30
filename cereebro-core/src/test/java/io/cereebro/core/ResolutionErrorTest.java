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
import java.net.URI;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * {@link ResolutionError} unit tests.
 * 
 * @author michaeltecourt
 */
public class ResolutionErrorTest {

    private static final String MESSAGE = "message";
    private static final URI SNITCH_URI = URI.create("http://cereebro.io");

    @Test
    public void verifyHashcodeEquals() {
        EqualsVerifier.forClass(ResolutionError.class).usingGetClass().verify();
    }

    @Test
    public void testConstructorsAndGetters() {
        ResolutionError error = ResolutionError.of(SNITCH_URI, MESSAGE);
        Assertions.assertThat(error.getMessage()).isEqualTo(MESSAGE);
        Assertions.assertThat(error.getSnitchUri()).isEqualTo(SNITCH_URI);
        Assertions.assertThat(error.getCause().isPresent()).isFalse();
    }

    @Test
    public void translateSnitchingException() {
        SnitchingException e = new SnitchingException(SNITCH_URI, MESSAGE);
        ResolutionError result = ResolutionError.translate(e);
        ResolutionError expected = ResolutionError.of(SNITCH_URI, MESSAGE);
        Assertions.assertThat(result).isEqualTo(expected);
        Assertions.assertThat(result.getCause().isPresent()).isTrue();
        Assertions.assertThat(result.getCause().get()).isEqualTo(e);
    }

    @Test
    public void testToString() {
        ResolutionError of = ResolutionError.of(SNITCH_URI, MESSAGE, new RuntimeException("whatever"));
        Assertions.assertThat(of.toString()).isNotEmpty();
    }

    @Test
    public void getStackTraceStringShouldContainExceptionMessage() {
        String exceptionMessage = "unit test";
        ResolutionError error = ResolutionError.of(SNITCH_URI, MESSAGE, new RuntimeException(exceptionMessage));
        Assertions.assertThat(error.hasCause()).isTrue();
        Assertions.assertThat(error.getCauseStackTraceString().isPresent()).isTrue();
        Assertions.assertThat(error.getCauseStackTraceString().get()).contains(exceptionMessage);
    }

    @Test
    public void getStackTraceStringShouldBeAbsent() {
        ResolutionError error = ResolutionError.of(SNITCH_URI, MESSAGE);
        Assertions.assertThat(error.hasCause()).isFalse();
        Assertions.assertThat(error.getCauseStackTraceString().isPresent()).isFalse();
    }

    @Test
    public void close() throws IOException {
        Closeable closeableMock = Mockito.mock(Closeable.class);
        Mockito.doThrow(new IOException("unit test")).when(closeableMock).close();
        ResolutionError.closeQuietly(closeableMock);
    }

}

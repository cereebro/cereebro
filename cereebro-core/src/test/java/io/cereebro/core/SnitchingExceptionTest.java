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

import org.junit.Assert;
import org.junit.Test;

public class SnitchingExceptionTest {

    @Test
    public void uriAndMessageConstructor() {
        URI uri = URI.create("fake://nope");
        String message = "test";
        SnitchingException e = new SnitchingException(uri, message);
        Assert.assertEquals(uri, e.getSnitchUri());
        Assert.assertEquals(message, e.getMessage());
    }

    @Test
    public void throwableAndUriConstructor() {
        URI uri = URI.create("fake://nope");
        Throwable cause = new RuntimeException("unit test");
        SnitchingException e = new SnitchingException(uri, cause);
        Assert.assertEquals(uri, e.getSnitchUri());
        Assert.assertEquals(cause, e.getCause());
    }
}

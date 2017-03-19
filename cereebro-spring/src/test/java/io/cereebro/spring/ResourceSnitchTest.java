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
package io.cereebro.spring;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentRelationships;
import io.cereebro.core.Consumer;
import io.cereebro.core.Dependency;
import io.cereebro.core.SnitchingException;
import io.cereebro.core.SystemFragment;

/**
 * {@link ResourceSnitch} unit tests.
 * 
 * @author michaeltecourt
 */
public class ResourceSnitchTest {

    private static final String RESOURCE_PATH = "/system-fragment-gambit.json";
    private ClassPathResource classpathResource;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        classpathResource = new ClassPathResource(RESOURCE_PATH);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void unreadableResourceURIShouldThrowIllegalStateException() throws IOException {
        Resource resourceMock = Mockito.mock(Resource.class);
        Mockito.when(resourceMock.getURI()).thenThrow(new IOException("unit test"));
        ResourceSnitch snitch = ResourceSnitch.of(objectMapper, resourceMock);
        try {
            snitch.getUri();
            Assert.fail("Expected IllegalStateException");
        } catch (IllegalStateException e) {
            // ok
            Mockito.verify(resourceMock).getURI();
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void errorReadingResourceShouldThrowSnitchingException() throws IOException {
        ObjectMapper objectMapperMock = Mockito.mock(ObjectMapper.class);
        Mockito.when(objectMapperMock.readValue(Mockito.any(InputStream.class), Mockito.any(Class.class)))
                .thenThrow(new IOException("unit test"));
        ResourceSnitch snitch = ResourceSnitch.of(objectMapperMock, classpathResource);
        try {
            snitch.snitch();
            Assert.fail("Expected SnitchingException");
        } catch (SnitchingException e) {

        }

    }

    @Test
    public void parse() {
        // @formatter:off
        ComponentRelationships rel = ComponentRelationships.builder()
                .component(Component.of("gambit", "superhero"))
                .addDependency(Dependency.on(Component.of("rogue", "superhero")))
                .addDependency(Dependency.on(Component.of("cards", "addiction")))
                .addConsumer(Consumer.by(Component.of("apocalypse", "villain")))
                .addConsumer(Consumer.by(Component.of("angel", "superhero")))
                .build();
        // @formatter:on
        SystemFragment expected = SystemFragment.of(rel);

        ResourceSnitch snitch = ResourceSnitch.of(objectMapper, new ClassPathResource(RESOURCE_PATH));
        SystemFragment actual = snitch.snitch();
        Assert.assertEquals(expected, actual);
    }

    /**
     * The actual URI returned by Spring Resource is the file absolute path,
     * which we don't know for sure. We just make sure the relative path is part
     * of the absolute URI.
     */
    @Test
    public void locationShouldMatchResourceURI() {
        ResourceSnitch snitch = ResourceSnitch.of(objectMapper, new ClassPathResource(RESOURCE_PATH));
        Assert.assertTrue(snitch.getUri().toString().contains(RESOURCE_PATH));
    }

}

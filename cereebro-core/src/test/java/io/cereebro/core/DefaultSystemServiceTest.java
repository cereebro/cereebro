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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * {@link DefaultSystemService} unit tests.
 * 
 * @author michaeltecourt
 */
public class DefaultSystemServiceTest {

    private static final String NAME = "xmen";
    private SystemResolver systemResolver;
    private SnitchRegistry snitchRegistry;
    private DefaultSystemService service;

    @Before
    public void setUp() {
        systemResolver = Mockito.mock(SystemResolver.class);
        snitchRegistry = Mockito.mock(SnitchRegistry.class);
        service = new DefaultSystemService(NAME, systemResolver, snitchRegistry);
    }

    @Test(expected = NullPointerException.class)
    public void constructorWithNullNameShouldThrowNullPointerException() {
        new DefaultSystemService(null, systemResolver, snitchRegistry);
    }

    @Test(expected = NullPointerException.class)
    public void constructorWithNullResolverShouldThrowNullPointerException() {
        new DefaultSystemService(NAME, null, snitchRegistry);
    }

    @Test(expected = NullPointerException.class)
    public void constructorWithNullRegistryShouldThrowNullPointerException() {
        new DefaultSystemService(NAME, systemResolver, null);
    }

    @Test
    public void getSystem() {
        Mockito.when(systemResolver.resolve(NAME, snitchRegistry)).thenReturn(System.empty(NAME));
        Assert.assertEquals(System.empty(NAME), service.get());
    }

}

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

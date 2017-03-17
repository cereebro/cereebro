package io.cereebro.server;

import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.web.servlet.ModelAndView;

import io.cereebro.core.System;
import io.cereebro.core.SystemService;
import io.cereebro.server.graph.sigma.Graph;

/**
 * {@link CereebroSystemController} unit tests.
 * 
 * @author michaeltecourt
 */
@RunWith(MockitoJUnitRunner.class)
public class SystemControllerTest {

    private static final String SYSTEM_NAME = "xmen";

    @Mock
    private SystemService systemServiceMock;
    @InjectMocks
    private CereebroSystemController controller;

    @Test
    public void emptySystem() {
        Mockito.when(systemServiceMock.get()).thenReturn(System.empty(SYSTEM_NAME));
        ModelAndView result = controller.systemPage();
        ModelAndViewAssert.assertViewName(result, "cereebro/system");
        ModelAndViewAssert.assertModelAttributeValue(result, "system", System.empty(SYSTEM_NAME));
        Graph expectedEmptyGraph = Graph.create(new HashSet<>(), new HashSet<>());
        ModelAndViewAssert.assertModelAttributeValue(result, "graph", expectedEmptyGraph);
    }

}

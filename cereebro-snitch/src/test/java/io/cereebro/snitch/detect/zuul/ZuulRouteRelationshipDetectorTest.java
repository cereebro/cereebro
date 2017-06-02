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
package io.cereebro.snitch.detect.zuul;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;

/**
 * {@link ZuulRouteRelationshipDetector} unit tests.
 * 
 * @author michaeltecourt
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ZuulRouteRelationshipDetectorTest {

    @Mock
    RouteLocator routeLocatorMock;

    @InjectMocks
    ZuulRouteRelationshipDetector detector;

    @Test
    public void routeWithLocationUrlShouldNotContainName() {
        Route route = routeWithLocation("http://nope.nope");
        Assertions.assertThat(detector.containsComponentName(route)).isFalse();
    }

    @Test
    public void routeWithHttpsUrlLocationShouldNotContainName() {
        Route route = routeWithLocation("https://nope.nope");
        Assertions.assertThat(detector.containsComponentName(route)).isFalse();
    }

    @Test
    public void routeShouldContainName() {
        Route route = routeWithLocation("yass");
        Assertions.assertThat(detector.containsComponentName(route)).isTrue();
    }

    @Test
    public void detect() {
        String name = "yass";
        List<Route> routes = Arrays.asList(routeWithLocation(name), routeWithLocation("https://nope.nope"),
                routeWithLocation("http://nope.nope"));
        Mockito.when(routeLocatorMock.getRoutes()).thenReturn(routes);
        Set<Relationship> result = detector.detect();
        Component expectedComponent = Component.of(name, ComponentType.HTTP_APPLICATION);
        Set<Relationship> expected = new HashSet<>(Arrays.asList(Dependency.on(expectedComponent)));
        Assertions.assertThat(result).isEqualTo(expected);
    }

    /**
     * Create a route with a given location. We don't care about other
     * attributes.
     * 
     * @param location
     *            The location to use.
     * @return a Route with the given location.
     */
    private static Route routeWithLocation(String location) {
        return new Route("id", "/whatever", location, "", false, Collections.emptySet());
    }
}

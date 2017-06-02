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

import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import io.cereebro.core.RelationshipDetector;

/**
 * Detects applications downstream of a Zuul proxy based on its routes.
 * 
 * @author michaeltecourt
 */
public class ZuulRouteRelationshipDetector implements RelationshipDetector {

    private final RouteLocator routeLocator;
    private final Pattern routeLocationExclusion;

    /**
     * Detects the downstream applications of a Zuul proxy based on its routes.
     * 
     * @param routeLocator
     *            Provides the exhaustive list of {@link Route}s managed by the
     *            Zuul proxy.
     */
    public ZuulRouteRelationshipDetector(RouteLocator routeLocator) {
        this.routeLocator = Objects.requireNonNull(routeLocator, "RouteLocator required");
        this.routeLocationExclusion = Pattern.compile("^(http|https)://.*$");
    }

    @Override
    public Set<Relationship> detect() {
        // @formatter:off
        return routeLocator.getRoutes().stream()
                .filter(this::containsComponentName)
                .map(this::createDependency)
                .collect(Collectors.toSet());
        // @formatter:on
    }

    /**
     * Create a {@link Dependency} on the application that is targeted by a Zuul
     * {@link Route}.
     * 
     * @param route
     *            Zuul Route.
     * @return a Dependecy on the route target application.
     */
    protected Dependency createDependency(Route route) {
        return Dependency.on(Component.of(route.getLocation(), ComponentType.HTTP_APPLICATION));
    }

    /**
     * See if we can extract a component name out of the {@link Route}. The
     * route location contains either a serviceId we can use, or a URL that we
     * can't use.
     * 
     * @param route
     *            Zuul Route.
     * @return {@code true} if the Route contains a Component name we can
     *         extract, {@code false} otherwise.
     */
    protected boolean containsComponentName(Route route) {
        return !routeLocationExclusion.matcher(route.getLocation()).matches();
    }

}

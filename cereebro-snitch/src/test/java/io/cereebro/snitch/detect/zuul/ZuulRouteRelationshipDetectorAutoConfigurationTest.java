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
import java.util.HashSet;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import io.cereebro.snitch.detect.zuul.ZuulRouteRelationshipDetectorAutoConfigurationTest.ZuulRouteRelationshipDetectorTestApplication;

/**
 * {@link ZuulRouteRelationshipDetector} unit tests.
 * 
 * @author michaeltecourt
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ZuulRouteRelationshipDetectorTestApplication.class,
        ZuulRouteRelationshipDetectorAutoConfiguration.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles({ "zuul", "nodb" })
public class ZuulRouteRelationshipDetectorAutoConfigurationTest {

    @Autowired
    ZuulRouteRelationshipDetector detector;

    @Test
    public void detectShouldReturnRouteWithServiceIdAndWithoutUrl() {
        Set<Relationship> result = detector.detect();
        Dependency component = Dependency.on(Component.of("x", ComponentType.HTTP_APPLICATION));
        Set<Relationship> expected = new HashSet<>(Arrays.asList(component));
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @EnableZuulProxy
    @SpringBootApplication
    static class ZuulRouteRelationshipDetectorTestApplication {

    }

}

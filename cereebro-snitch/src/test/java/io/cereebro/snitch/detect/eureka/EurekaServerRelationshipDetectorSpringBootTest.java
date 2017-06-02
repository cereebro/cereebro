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
package io.cereebro.snitch.detect.eureka;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.netflix.discovery.EurekaClient;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import io.cereebro.snitch.detect.eureka.EurekaServerRelationshipDetectorSpringBootTest.EurekaServerRelationshipDetectorSpringBootTestApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EurekaServerRelationshipDetectorSpringBootTestApplication.class)
public class EurekaServerRelationshipDetectorSpringBootTest {

    @Autowired
    EurekaServerRelationshipDetector detector;

    @Test
    public void test() {
        Set<Relationship> rels = Dependency.on(Component.of("eureka-server", ComponentType.HTTP_APPLICATION_REGISTRY))
                .asRelationshipSet();
        Assertions.assertThat(detector.detect()).isEqualTo(rels);
    }

    @SpringBootApplication
    static class EurekaServerRelationshipDetectorSpringBootTestApplication {

        @MockBean
        EurekaClient eurekaClientMock;

    }

}

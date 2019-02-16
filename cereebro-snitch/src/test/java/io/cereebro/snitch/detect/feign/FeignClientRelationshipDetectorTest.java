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
package io.cereebro.snitch.detect.feign;

import java.util.HashMap;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Iterables;

import io.cereebro.core.ComponentType;
import io.cereebro.core.Relationship;
import io.cereebro.snitch.detect.feign.FeignClientRelationshipDetectorTest.FeignClientTestApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FeignClientTestApplication.class)
public class FeignClientRelationshipDetectorTest {

    @Autowired
    private FeignClientAnnotationRelationshipDetector detector;

    @Test
    public void testFeignClientIsFound() {
        Set<Relationship> relations = detector.detect();
        Assertions.assertThat(relations).isNotEmpty();
        Assertions.assertThat(relations).hasSize(1);
        Relationship relation = Iterables.getFirst(relations, null);
        Assertions.assertThat(relation.getComponent().getName()).isEqualTo("dummy-api");
        Assertions.assertThat(relation.getComponent().getType()).isEqualTo(ComponentType.HTTP_APPLICATION);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testMethodDetectorShouldThrowUnsupportedOperationException() {
        detector.extractFromAnnotationAttributes(new HashMap<>());
    }

    @SpringBootApplication
    @EnableFeignClients
    static class FeignClientTestApplication {

        @FeignClient(name = "dummy-api", url = "http://localhost", path = "/sample-api")
        public static interface AFeignClient {

        }

    }

}

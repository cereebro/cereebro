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
package io.cereebro.snitch.detect.redis;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import io.cereebro.snitch.detect.redis.RedisRelationshipDetectorSentinelTest.RedisRelationshipDetectorSentinelTestApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisRelationshipDetectorSentinelTestApplication.class, value = "spring.redis.sentinel.master=apocalypse")
@ActiveProfiles("nodb")
public class RedisRelationshipDetectorSentinelTest {

    @Autowired
    RedisProperties props;

    @Autowired
    RedisRelationshipDetector detector;

    @Test
    public void shouldReturnRedisSentinelMasterName() {
        Dependency dependency = Dependency.on(Component.of("apocalypse", ComponentType.REDIS));
        Set<Relationship> rels = new HashSet<>(Arrays.asList(dependency));
        Assertions.assertThat(detector.detect()).isEqualTo(rels);
    }

    @SpringBootApplication
    @EnableConfigurationProperties(RedisProperties.class)
    static class RedisRelationshipDetectorSentinelTestApplication {

        @MockBean
        RedisConnectionFactory mock;

    }

}

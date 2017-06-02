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
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Sentinel;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;

/**
 * {@link RedisRelationshipDetector} unit tests.
 * 
 * @author michaeltecourt
 */
public class RedisRelationshipDetectorTest {

    @Test
    public void nullSentinelShouldReturnDefaultComponentName() {
        RedisConnectionFactory factory = Mockito.mock(RedisConnectionFactory.class);
        RedisRelationshipDetector detector = new RedisRelationshipDetector(new RedisProperties(),
                Arrays.asList(factory));
        Set<Relationship> rels = new HashSet<>();
        rels.add(Dependency.on(Component.of("default", ComponentType.REDIS)));
        Assertions.assertThat(detector.detect()).isEqualTo(rels);
    }

    @Test
    public void nullArgsShouldReturnEmptyRels() {
        RedisRelationshipDetector detector = new RedisRelationshipDetector(null, null);
        Assertions.assertThat(detector.detect()).isEmpty();
    }

    @Test
    public void shouldUseSentinelMasterName() {
        String name = "name";
        RedisConnectionFactory factory = Mockito.mock(RedisConnectionFactory.class);
        RedisProperties props = new RedisProperties();
        props.setSentinel(new Sentinel());
        props.getSentinel().setMaster(name);
        RedisRelationshipDetector detector = new RedisRelationshipDetector(props, Arrays.asList(factory));
        Set<Relationship> rels = new HashSet<>();
        rels.add(Dependency.on(Component.of(name, ComponentType.REDIS)));
        Assertions.assertThat(detector.detect()).isEqualTo(rels);
    }

}

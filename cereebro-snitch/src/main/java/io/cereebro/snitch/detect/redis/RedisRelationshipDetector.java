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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import io.cereebro.core.RelationshipDetector;
import lombok.Getter;
import lombok.Setter;

/**
 * Redis RelationshipDetector. Aside from a Redis sentinel master, nothing
 * carries a name in Redis, therefore for a basic connection we can only detect
 * a relationship to a typed Redis component with a default name.
 * 
 * @author michaeltecourt
 *
 */
public class RedisRelationshipDetector implements RelationshipDetector {

    @Getter
    @Setter
    private String defaultName = "default";

    private final RedisProperties redisProperties;
    private final List<RedisConnectionFactory> connectionFactories;

    /**
     * Redis RelationshipDetector.
     * 
     * @param redisProperties
     *            Redis configuration properties (nullable).
     * @param redisConnectionFactories
     *            Available RedisConnectionFactory instances (nullable).
     */
    public RedisRelationshipDetector(RedisProperties redisProperties,
            List<RedisConnectionFactory> redisConnectionFactories) {
        this.redisProperties = redisProperties;
        this.connectionFactories = new ArrayList<>();
        if (!CollectionUtils.isEmpty(redisConnectionFactories)) {
            this.connectionFactories.addAll(redisConnectionFactories);
        }
    }

    @Override
    public Set<Relationship> detect() {
        if (CollectionUtils.isEmpty(connectionFactories)) {
            return Collections.emptySet();
        }
        String sentinelMaster = getRedisSentinelMasterName();
        String name = StringUtils.hasText(sentinelMaster) ? sentinelMaster : defaultName;
        return Dependency.on(Component.of(name, ComponentType.REDIS)).asRelationshipSet();
    }

    /**
     * Returns the name of the Redis Sentinel master, extracted from
     * configuration properties.
     * 
     * @return Nullable Redis Sentinel master name.
     */
    private String getRedisSentinelMasterName() {
        if (redisProperties != null && redisProperties.getSentinel() != null) {
            return redisProperties.getSentinel().getMaster();
        }
        return null;
    }

}

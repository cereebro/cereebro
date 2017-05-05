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
package io.cereebro.snitch.detect.mongo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;

/**
 * MongoDB detector auto configuration, depending on MongoDb classes being
 * available on the classpath. Requires a separate configuration class to
 * prevent issues with optional classes.
 * 
 * @author lwarrot
 */
@Configuration
@ConditionalOnClass(MongoClient.class)
public class MongoDbRelationshipDetectorAutoConfiguration {

    @Autowired(required = false)
    private List<MongoClient> clients;

    @Bean
    public MongoDbRelationshipDetector mongoDBRelationshipDetector() {
        return new MongoDbRelationshipDetector(clients);
    }

}

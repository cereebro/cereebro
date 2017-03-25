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
package io.cereebro.spring.boot.autoconfigure.mongo;

import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.spring.boot.autoconfigure.mongo.MongoDbRelationshipDetectorTest.MongoDbTestApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongoDbTestApplication.class)
public class MongoDbRelationshipDetectorTest {

    @Autowired
    private MongoDbRelationshipDetector detector;

    @Test
    public void mongoRelationshipWithSessionAvailable() {
        Assertions.assertThat(detector.detect())
                .contains(Dependency.on(Component.of("mymongo", ComponentType.MONGODB)));
    }

    @SpringBootApplication
    public static class MongoDbTestApplication {

        @Bean
        public MongoClient mongoClient() {
            MongoClient result = Mockito.mock(MongoClient.class);
            DB mockDB = Mockito.mock(DB.class);
            when(mockDB.getName()).thenReturn("mymongo");
            Mockito.when(result.getUsedDatabases()).thenReturn(Arrays.asList(mockDB));
            return result;
        }
    }

}

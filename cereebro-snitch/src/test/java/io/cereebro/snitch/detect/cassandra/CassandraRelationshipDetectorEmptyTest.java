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
package io.cereebro.snitch.detect.cassandra;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.snitch.detect.cassandra.CassandraRelationshipDetector;
import io.cereebro.snitch.detect.cassandra.CassandraRelationshipDetectorEmptyTest.MissingCassandraTestApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MissingCassandraTestApplication.class)
public class CassandraRelationshipDetectorEmptyTest {

    @Autowired
    private CassandraRelationshipDetector detector;

    @Test
    public void cassandraRelationshipWithNoSessionAvailable() {
        Assertions.assertThat(detector.detect()).isEmpty();
    }

    @SpringBootApplication(exclude = { MongoAutoConfiguration.class })
    static class MissingCassandraTestApplication {

    }

}
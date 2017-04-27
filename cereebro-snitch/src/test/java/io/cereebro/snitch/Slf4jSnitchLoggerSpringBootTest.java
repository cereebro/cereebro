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
package io.cereebro.snitch;

import java.io.IOException;
import java.net.URI;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.snitch.Slf4jSnitchLoggerSpringBootTest.LogSnitchSpringBootTestApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LogSnitchSpringBootTestApplication.class, value = { "cereebro.snitch.logOnStartup=true",
        "spring.jackson.serialization.indent_output=true" })
public class Slf4jSnitchLoggerSpringBootTest {

    static final URI SNITCH_URI = URI.create("http://cereebro.io/nope");

    @Autowired
    private Slf4jSnitchLogger logSnitch;

    @Rule
    public OutputCapture capture = new OutputCapture();

    /**
     * Cannot capture what happens when context is loading, so we have to fire
     * the log method again. The fact that a {@link Slf4jSnitchLogger} instance
     * is available will be enough evidence that auto-configuration has gone
     * well.
     * 
     * @throws IOException
     */
    @Test
    public void logsShouldContainStatementAndFragment() throws IOException {
        logSnitch.log();
        Assertions.assertThat(capture.toString()).contains(Slf4jSnitchLogger.class.getSimpleName());
    }

    @SpringBootApplication(exclude = { MongoAutoConfiguration.class, RabbitAutoConfiguration.class })
    static class LogSnitchSpringBootTestApplication {

    }

}

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
package io.cereebro.spring.boot.autoconfigure.log;

import java.io.IOException;
import java.net.URI;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import io.cereebro.core.Snitch;
import io.cereebro.core.SystemFragment;
import io.cereebro.spring.boot.autoconfigure.log.Slf4jLogSnitchSpringBootTest.LogSnitchSpringBootTestApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LogSnitchSpringBootTestApplication.class, value = "cereebro.snitch.logOnStartup=true")
public class Slf4jLogSnitchSpringBootTest {

    static final URI SNITCH_URI = URI.create("http://cereebro.io/nope");

    @Autowired
    private Slf4jLogSnitch logSnitch;

    @Rule
    public OutputCapture capture = new OutputCapture();

    /**
     * Cannot capture what happens when context is loading, so we have to fire
     * the log method again. The fact that a {@link Slf4jLogSnitch} instance is
     * available will be enough evidence that auto-configuration has gone well.
     * 
     * @throws IOException
     */
    @Test
    public void logsShouldContainStatementAndFragment() throws IOException {
        logSnitch.log();
        Assertions.assertThat(capture.toString()).contains(SNITCH_URI.toString());
    }

    @SpringBootApplication
    static class LogSnitchSpringBootTestApplication {

        @Bean
        public Snitch snitchMock() {
            Snitch snitchMock = Mockito.mock(Snitch.class);
            Mockito.when(snitchMock.snitch()).thenReturn(SystemFragment.empty());
            Mockito.when(snitchMock.getUri()).thenReturn(SNITCH_URI);
            return snitchMock;
        }

    }

}

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
package io.cereebro.snitch.detect.ldap;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.ldap.core.ContextSource;

import io.cereebro.core.Component;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;

/**
 * {@link LdapRelationshipDetector} unit tests.
 * 
 * @author michaeltecourt
 *
 */
public class LdapRelationshipDetectorTest {

    @Test
    public void detectNullShouldReturnEmptyDependencies() {
        Set<Relationship> actual = new LdapRelationshipDetector(null).detect();
        Assertions.assertThat(actual).isEmpty();
    }

    @Test
    public void detectEmptyContextSourcesShouldReturnEmptyDependencies() {
        Set<Relationship> actual = new LdapRelationshipDetector(Collections.emptyList()).detect();
        Assertions.assertThat(actual).isEmpty();
    }

    @Test
    public void detectSingleContextSourceShouldReturnSingleDependency() {
        ContextSource ctx = Mockito.mock(ContextSource.class);
        LdapRelationshipDetector detector = new LdapRelationshipDetector(Arrays.asList(ctx));
        Set<Relationship> actual = detector.detect();
        Dependency dependency = Dependency.on(Component.of("default", "directory/ldap"));
        Set<Relationship> expected = new HashSet<>();
        expected.add(dependency);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void detectManyContextSourcesShouldReturnSingleDependency() {
        List<ContextSource> contextSources = Arrays.asList(Mockito.mock(ContextSource.class),
                Mockito.mock(ContextSource.class));
        LdapRelationshipDetector detector = new LdapRelationshipDetector(contextSources);
        Set<Relationship> actual = detector.detect();
        Dependency dependency = Dependency.on(Component.of("default", "directory/ldap"));
        Set<Relationship> expected = new HashSet<>();
        expected.add(dependency);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void detectContextSourceWithCustomNameShouldCustomizeDependency() {
        ContextSource ctx = Mockito.mock(ContextSource.class);
        LdapRelationshipDetector detector = new LdapRelationshipDetector(Arrays.asList(ctx));
        String name = "dir";
        detector.setDefaultName(name);
        Set<Relationship> actual = detector.detect();
        Dependency dependency = Dependency.on(Component.of(name, "directory/ldap"));
        Set<Relationship> expected = new HashSet<>();
        expected.add(dependency);
        Assertions.assertThat(detector.getDefaultName()).isEqualTo(name);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

}

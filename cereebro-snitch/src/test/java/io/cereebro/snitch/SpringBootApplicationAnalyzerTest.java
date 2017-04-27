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

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.collect.Maps;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentRelationships;
import io.cereebro.core.RelationshipDetector;
import io.cereebro.core.SystemFragment;
import io.cereebro.snitch.CereebroProperties;
import io.cereebro.snitch.ComponentProperties;
import io.cereebro.snitch.SpringBootApplicationAnalyzer;

public class SpringBootApplicationAnalyzerTest {

    private CereebroProperties props;
    private RelationshipDetector detectorMock;
    private SpringBootApplicationAnalyzer analyzer;

    @Before
    public void setUp() {
        detectorMock = Mockito.mock(RelationshipDetector.class);
        props = new CereebroProperties();
        ComponentProperties app = props.getApplication().getComponent();
        app.setName("name");
        app.setType("type");
        analyzer = new SpringBootApplicationAnalyzer(props, Arrays.asList(detectorMock));
    }

    @Test
    public void uritpl() {
        HashMap<String, String> params = Maps.newHashMap();
        params.put("lang", "fr");
        params.put("year", "3010");
        params.put("reportId", "1234");
        String uri = UriComponentsBuilder.fromUriString("http://luminus-{lang}.toto.org/#/{reportId}")
                .buildAndExpand(params).toUriString();
        System.out.println(uri);
    }

    @Test
    public void analyzeSystem() {
        Mockito.when(detectorMock.detect()).thenReturn(new HashSet<>());
        SystemFragment result = analyzer.analyzeSystem();
        ComponentRelationships rels = ComponentRelationships.builder().component(Component.of("name", "type")).build();
        Assertions.assertThat(result).isEqualTo(SystemFragment.of(rels));
    }

}

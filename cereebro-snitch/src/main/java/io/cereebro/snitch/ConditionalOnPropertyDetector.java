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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;

import io.cereebro.snitch.ConditionalOnPropertyDetector.ConditionalDetector;

/**
 * Conditional annotation for disable a detector.<br>
 * 
 * application.properties:<br>
 * 
 * <code>
 * cereebro.snitch.detect.cassandra.enabled = false # Disable the cassandra detector
 * </code>
 * 
 * @author lwarrot
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Conditional(ConditionalDetector.class)
public @interface ConditionalOnPropertyDetector {

    public static final String DETECTOR_PREFIX = "cereebro.snitch.detect";

    String value();

    String prefix() default DETECTOR_PREFIX;

    public class ConditionalDetector implements Condition {

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            final Map<String, Object> annotationValues = metadata
                    .getAnnotationAttributes(ConditionalOnPropertyDetector.class.getName());
            final String name = String.class.cast(annotationValues.get("value"));
            final String prefix = String.class.cast(annotationValues.get("prefix"));
            final String value = context.getEnvironment().getProperty(prefix + "." + name + ".enabled", "true");
            return "true".equals(value);
        }

    }

}

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
package io.cereebro.snitch.detect;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Matches when a relationship detector has its {@literal enabled} property set
 * to {@code true} (matches by default).
 * 
 * @author lucwarrot
 * @author michaeltecourt
 */
public class OnEnabledDetectorCondition extends SpringBootCondition {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        AnnotationAttributes attributes = AnnotationAttributes
                .fromMap(metadata.getAnnotationAttributes(ConditionalOnEnabledDetector.class.getName()));
        final String name = attributes.getString("value");
        final String prefix = attributes.getString("prefix");
        Boolean enabled = context.getEnvironment().getProperty(prefix + "." + name + ".enabled", Boolean.class, true);
        return new ConditionOutcome(enabled, ConditionMessage.forCondition(ConditionalOnEnabledDetector.class, name)
                .because(enabled ? "enabled" : "disabled"));
    }

}
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
package io.cereebro.spring.annotation;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.type.MethodMetadata;

import io.cereebro.core.Relationship;
import io.cereebro.core.RelationshipDetector;
import lombok.extern.slf4j.Slf4j;

/**
 * Abstract class implementing {@link RelationshipDetector} for
 * {@link Annotation}. The child class have to declare the type of annotation to
 * detect.
 * 
 * @author lwarrot
 *
 * @param <T>
 *            Annotation to detect.
 */
@Slf4j
public abstract class AnnotationRelationshipDetector<T extends Annotation>
        implements RelationshipDetector, ApplicationContextAware {

    private ConfigurableApplicationContext applicationContext;
    private final Class<T> annotation;

    public AnnotationRelationshipDetector(Class<T> annotation) {
        this.annotation = Objects.requireNonNull(annotation, "Annotation class required");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        if (applicationContext instanceof ConfigurableApplicationContext) {
            this.applicationContext = ConfigurableApplicationContext.class.cast(applicationContext);
        }
    }

    @Override
    public Set<Relationship> detect() {
        Set<Relationship> result = new HashSet<>();
        if (applicationContext != null) {

            Set<Relationship> annotatedClassRels = detectAnnotatedClasses();
            LOGGER.debug("Found {} bean classes annotated with {}", annotatedClassRels.size(), annotation);
            result.addAll(annotatedClassRels);

            Set<Relationship> annotatedFactoryMethodRels = detectAnnotatedFactoryMethods();
            LOGGER.debug("Found {} bean factory methods annotated with {}", annotatedFactoryMethodRels.size(),
                    annotation);
            result.addAll(annotatedFactoryMethodRels);
        }
        return result;
    }

    /**
     * Detected relationships in annotated bean classes.
     * 
     * @return Relationships detected from annotated classes.
     */
    protected Set<Relationship> detectAnnotatedClasses() {
        Set<Relationship> result = new HashSet<>();
        String[] annotatedBeans = applicationContext.getBeanNamesForAnnotation(annotation);
        for (String beanName : annotatedBeans) {
            T anno = applicationContext.findAnnotationOnBean(beanName, annotation);
            result.addAll(extractFromAnnotation(anno));
        }
        return result;
    }

    /**
     * Detect relationships in annotated {@link Bean @Bean} Factory methods.
     * 
     * @return Relationships detected from factory methods.
     */
    protected Set<Relationship> detectAnnotatedFactoryMethods() {
        Set<Relationship> result = new HashSet<>();
        /* retrieve all beans declared in the application context */
        String[] annotateBeans = applicationContext.getBeanDefinitionNames();
        ConfigurableBeanFactory factory = applicationContext.getBeanFactory();
        for (String beanName : annotateBeans) {
            /* ... and get the bean definition of each declared beans */
            Optional<MethodMetadata> metadata = getMethodMetadata(factory.getMergedBeanDefinition(beanName));
            if (metadata.isPresent()) {
                Set<Relationship> rel = detectMethodMetadata(metadata.get());
                result.addAll(rel);
            }
        }
        return result;
    }

    protected Optional<MethodMetadata> getMethodMetadata(BeanDefinition beanDefinition) {
        if (beanDefinition instanceof AnnotatedBeanDefinition) {
            return Optional.of(AnnotatedBeanDefinition.class.cast(beanDefinition).getFactoryMethodMetadata());
        }
        return Optional.empty();
    }

    protected Set<Relationship> detectMethodMetadata(final MethodMetadata metadata) {
        /*
         * ... get the metadata of the current definition bean for the
         * annotation DependencyHint. In this case, we retrieve the annotation
         * on the annotated method @Bean. The map can be null.
         */
        Map<String, Object> hintData = metadata.getAnnotationAttributes(annotation.getName());
        /*
         * ... get the Hint directly from the class (Target = ElementType.TYPE)
         */
        Optional<T> methodAnnotation = getAnnotation(metadata);
        Set<Relationship> rel = new HashSet<>();
        if (hintData != null && !hintData.isEmpty()) {
            rel.addAll(extractFromAnnotationAttributes(hintData));
        } else if (methodAnnotation.isPresent()) {
            rel.addAll(extractFromAnnotation(methodAnnotation.get()));
        }
        return rel;
    }

    /**
     * Extract a Relationship from a typed annotation. Used when the annotation
     * is set on a class.
     * 
     * @param annotation
     *            Target annotation.
     * @return Relationship
     */
    protected abstract Set<Relationship> extractFromAnnotation(T annotation);

    /**
     * Extract a Relationship from the annotation represented as a Map. Used
     * when the annotation is set on a {@link Bean} factory method instead of a
     * class.
     * 
     * @param annotationAttributes
     *            Target annotation attributes map.
     * @return Relationship
     */
    protected abstract Set<Relationship> extractFromAnnotationAttributes(Map<String, Object> annotationAttributes);

    /**
     * Get the annotation from the class instead of the {@link Bean} method.
     * 
     * @param metadata
     *            Method metadata.
     * @return An optional target annotation.
     */
    protected Optional<T> getAnnotation(MethodMetadata metadata) {
        try {
            return Optional.ofNullable(Class.forName(metadata.getReturnTypeName()).getDeclaredAnnotation(annotation));
        } catch (ClassNotFoundException e) {
            LOGGER.error("Could not load class : " + metadata.getReturnTypeName(), e);
        }
        return Optional.empty();
    }

}

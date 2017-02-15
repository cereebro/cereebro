package io.cereebro.autoconfigure.annotation;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.StandardMethodMetadata;

import io.cereebro.core.Relationship;
import io.cereebro.core.RelationshipDetector;
import io.cereebro.core.annotation.DependencyHint;

/**
 * Abstract class implementing {@link RelationshipDetector} for
 * {@link Annotation}. The child class have to declare the type of annotation to
 * detect.
 * 
 * @author lwarrot
 * @see DependencyHint
 *
 * @param <T>
 */
public abstract class AnnotationRelationshipDetector<T extends Annotation>
        implements RelationshipDetector, ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationRelationshipDetector.class);

    private ConfigurableApplicationContext applicationContext;
    private final Class<T> annotation;

    public AnnotationRelationshipDetector(Class<T> annotation) {
        this.annotation = annotation;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext instanceof ConfigurableApplicationContext) {
            this.applicationContext = ConfigurableApplicationContext.class.cast(applicationContext);
        }
    }

    @Override
    public Set<Relationship> detect() {
        Set<Relationship> result = new HashSet<>();
        if (applicationContext != null) {
            /* retrieve all beans declared in the application context */
            String[] annotateBeans = applicationContext.getBeanDefinitionNames();
            ConfigurableBeanFactory factory = applicationContext.getBeanFactory();
            for (String beanName : annotateBeans) {
                /* ... and get the bean definition of each declared beans */
                BeanDefinition beanDefinition = factory.getMergedBeanDefinition(beanName);
                if (beanDefinition.getSource() instanceof StandardMethodMetadata) {
                    StandardMethodMetadata metadata = StandardMethodMetadata.class.cast(beanDefinition.getSource());
                    /*
                     * ... get the metadata of the current definition bean for
                     * the annotation DependencyHint. In this case, we retrieve
                     * the annotation on the annotated method @Bean
                     */
                    Map<String, Object> hintData = metadata.getAnnotationAttributes(annotation.getName());
                    /*
                     * ... get the Hint directly from the class (Target =
                     * ElementType.TYPE)
                     */
                    T annotation = getAnnotation(metadata);
                    if (hintData != null && !hintData.isEmpty()) {
                        result.add(extractFromAnnotationAttributes(hintData));
                    } else if (annotation != null) {
                        result.add(extractFromAnnotation(annotation));
                    }
                }
            }
        }
        return result;
    }

    /**
     * Extract a Relationship from a typed annotation. Used when the annotation
     * is set on a class.
     * 
     * @param annotation
     * @return Relationship
     */
    protected abstract Relationship extractFromAnnotation(T annotation);

    /**
     * Extract a Relationship from the annotation represented as a Map. Used
     * when the annotation is set on a {@link Bean} factory method instead of a
     * class.
     * 
     * @param annotationAttributes
     * @return Relationship
     */
    protected abstract Relationship extractFromAnnotationAttributes(Map<String, Object> annotationAttributes);

    /**
     * Get the {@link DependencyHint} annotation from the class instead of the
     * {@link Bean} method.
     * 
     * @param metadata
     * @return
     */
    private T getAnnotation(MethodMetadata metadata) {
        try {
            return Class.forName(metadata.getReturnTypeName()).getDeclaredAnnotation(annotation);
        } catch (ClassNotFoundException e) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Could not load the class {}", metadata.getReturnTypeName());
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(e.getMessage(), e);
            }
        }
        return null;
    }

}

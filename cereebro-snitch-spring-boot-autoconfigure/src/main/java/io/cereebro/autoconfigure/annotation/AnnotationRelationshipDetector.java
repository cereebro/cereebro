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

import io.cereebro.core.Component;
import io.cereebro.core.Dependency;
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
                    Map<String, Object> dependencyHintData = metadata.getAnnotationAttributes(annotation.getName());
                    /*
                     * ... get the DependencyHint directly from the class
                     * (Target = ElementType.TYPE)
                     */
                    T annotationDependency = getAnnotation(metadata);
                    if (dependencyHintData != null && !dependencyHintData.isEmpty()) {
                        String name = String.class.cast(dependencyHintData.get(getNameKey()));
                        String type = String.class.cast(dependencyHintData.get(getTypeKey()));
                        result.add(Dependency.on(Component.of(name, type)));
                    } else if (annotationDependency != null) {
                        result.add(Dependency
                                .on(Component.of(getName(annotationDependency), getType(annotationDependency))));
                    }
                }
            }
        }
        return result;
    }

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

    /**
     * Return the name value for the {@link Component} from the generic
     * {@link Annotation}
     * 
     * @param annotation
     * @return
     */
    protected abstract String getName(T annotation);

    /**
     * Return the type value of the {@link Component} from the generic
     * {@link Annotation}
     * 
     * @param annotation
     * @return
     */
    protected abstract String getType(T annotation);

    /**
     * Return the key for the name of the {@link Component}
     * 
     * @return
     */
    protected abstract String getNameKey();

    /**
     * Return the key for the type of the {@link Component}
     * 
     * @return
     */
    protected abstract String getTypeKey();

}

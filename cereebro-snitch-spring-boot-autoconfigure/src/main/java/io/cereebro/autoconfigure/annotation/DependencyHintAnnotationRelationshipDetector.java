package io.cereebro.autoconfigure.annotation;

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
 * Implementation of {@link RelationshipDetector} used to detect all
 * {@link DependencyHint} declared in the {@link ApplicationContext}. The class
 * retrieves all {@link Bean} annotated with {@link DependencyHint}.
 * {@link Relationship}
 * 
 * @author lwarrot
 *
 */
public class DependencyHintAnnotationRelationshipDetector implements RelationshipDetector, ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(DependencyHintAnnotationRelationshipDetector.class);

    private ConfigurableApplicationContext applicationContext;

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
                    Map<String, Object> dependencyHintData = metadata
                            .getAnnotationAttributes(DependencyHint.class.getName());
                    /*
                     * ... get the DependencyHint directly from the class
                     * (Target = ElementType.TYPE)
                     */
                    DependencyHint dependencyHint = getAnnotation(metadata);
                    if (dependencyHintData != null && !dependencyHintData.isEmpty()) {
                        String name = String.class.cast(dependencyHintData.get("name"));
                        String type = String.class.cast(dependencyHintData.get("type"));
                        result.add(Dependency.on(Component.of(name, type)));
                    } else if (dependencyHint != null) {
                        result.add(Dependency.on(Component.of(dependencyHint.name(), dependencyHint.type())));
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
    private DependencyHint getAnnotation(MethodMetadata metadata) {
        try {
            return Class.forName(metadata.getReturnTypeName()).getDeclaredAnnotation(DependencyHint.class);
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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext instanceof ConfigurableApplicationContext) {
            this.applicationContext = ConfigurableApplicationContext.class.cast(applicationContext);
        }
    }

}

package io.cereebro.spring.cloud.autoconfigure.feign;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(value = FeignClient.class)
public class CereebroFeignClientAutoConfiguration {

    @Bean
    public FeignClientAnnotationRelationshipDetector feignClientAnnotationDependencyDetector() {
        return new FeignClientAnnotationRelationshipDetector();
    }

}

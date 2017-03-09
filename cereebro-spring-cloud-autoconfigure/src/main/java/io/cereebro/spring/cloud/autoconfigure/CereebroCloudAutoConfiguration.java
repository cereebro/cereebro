package io.cereebro.spring.cloud.autoconfigure;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.cereebro.spring.cloud.autoconfigure.eureka.CereebroEurekaInstanceAutoConfiguration;
import io.cereebro.spring.cloud.autoconfigure.feign.CereebroFeignClientAutoConfiguration;

@Configuration
@Import({ CereebroFeignClientAutoConfiguration.class, CereebroEurekaInstanceAutoConfiguration.class })
public class CereebroCloudAutoConfiguration {

}

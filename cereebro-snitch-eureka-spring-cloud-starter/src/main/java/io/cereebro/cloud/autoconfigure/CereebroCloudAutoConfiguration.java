package io.cereebro.cloud.autoconfigure;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.cereebro.cloud.autoconfigure.annotation.feign.CereebroFeignClientAutoConfiguration;
import io.cereebro.cloud.autoconfigure.eureka.CereebroEurekaAutoConfiguration;

@Configuration
@Import({ CereebroFeignClientAutoConfiguration.class, CereebroEurekaAutoConfiguration.class })
public class CereebroCloudAutoConfiguration {

}

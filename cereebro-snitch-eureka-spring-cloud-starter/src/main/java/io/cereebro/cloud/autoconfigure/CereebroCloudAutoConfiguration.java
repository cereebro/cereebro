package io.cereebro.cloud.autoconfigure;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.cereebro.cloud.autoconfigure.annotation.feign.CereebroFeignClientAutoConfiguration;

@Configuration
@Import(CereebroFeignClientAutoConfiguration.class)
public class CereebroCloudAutoConfiguration {

}

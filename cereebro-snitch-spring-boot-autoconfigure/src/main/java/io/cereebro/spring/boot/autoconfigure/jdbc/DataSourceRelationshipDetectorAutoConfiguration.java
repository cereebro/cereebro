package io.cereebro.spring.boot.autoconfigure.jdbc;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(DataSource.class)
public class DataSourceRelationshipDetectorAutoConfiguration {

    @Autowired(required = false)
    private List<DataSource> dataSources;

    @Bean
    public DataSourceRelationshipDetector dataSourceRelationshipDetector() {
        return new DataSourceRelationshipDetector(dataSources);
    }

}

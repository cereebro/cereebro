package io.cereebro.spring.boot.autoconfigure.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.util.CollectionUtils;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import io.cereebro.core.RelationshipDetector;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of {@link RelationshipDetector} used to detect all data source
 * dependencies. The class retrieves all {@link DataSource} available in the
 * context and create a {@link Relationship}
 * 
 * @author lwarrot
 *
 */
@Slf4j
public class DataSourceRelationshipDetector implements RelationshipDetector {

    private final List<DataSource> dataSources;

    private Set<Relationship> relationsCache;

    public DataSourceRelationshipDetector(Collection<DataSource> dataSources) {
        this.dataSources = new ArrayList<>();
        if (!CollectionUtils.isEmpty(dataSources)) {
            this.dataSources.addAll(dataSources);
        }
    }

    @PostConstruct
    protected void postConstruct() {
        relationsCache = detect();
    }

    @Override
    public Set<Relationship> detect() {
        if (relationsCache == null) {
            final Set<Relationship> result = new HashSet<>();
            for (DataSource ds : dataSources) {
                try {
                    result.add(Dependency.on(Component.of(ds.getConnection().getCatalog(), ComponentType.RELATIONAL_DATABASE)));
                } catch (SQLException e) {
                    if (LOGGER.isInfoEnabled()) {
                        LOGGER.info("could not fetch the default catalog of the database connection");
                    }
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug(e.getMessage(), e);
                    }
                }
            }
            relationsCache = result;
        }
        return new HashSet<>(relationsCache);
    }

}

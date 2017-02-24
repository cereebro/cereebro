package io.cereebro.autoconfigure.datasource;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.cereebro.core.Component;
import io.cereebro.core.ComponentType;
import io.cereebro.core.Dependency;
import io.cereebro.core.Relationship;
import io.cereebro.core.RelationshipDetector;

/**
 * Implementation of {@link RelationshipDetector} used to detect all data source
 * dependencies. The class retrieves all {@link DataSource} available in the
 * context and create a {@link Relationship}
 * 
 * @author lwarrot
 *
 */
public class DataSourceRelationshipDetector implements RelationshipDetector {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceRelationshipDetector.class);

    @Autowired(required = false)
    private Set<DataSource> dataSource;

    private Set<Relationship> relations = Collections.emptySet();

    @PostConstruct
    protected void postConstruct() {
        relations = detect();
    }

    @Override
    public Set<Relationship> detect() {
        if (!relations.isEmpty()) {
            return relations;
        }
        if (dataSource != null) {
            final Set<Relationship> result = new HashSet<>();
            for (DataSource ds : dataSource) {
                try {
                    result.add(Dependency.on(Component.of(ds.getConnection().getCatalog(), ComponentType.DATABASE)));
                } catch (SQLException e) {
                    if (LOGGER.isInfoEnabled()) {
                        LOGGER.info("could not fetch the default catalog of the database connection");
                    }
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug(e.getMessage(), e);
                    }
                }
            }
            relations = result;
        }
        return relations;
    }

}

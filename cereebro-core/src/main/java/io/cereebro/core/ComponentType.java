/*
 * Copyright © 2017 the original authors (http://cereebro.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.cereebro.core;

/**
 * Default component types.
 * 
 * @author michaeltecourt
 */
public class ComponentType {

    public static final String HTTP_APPLICATION = "application/http";
    public static final String HTTP_APPLICATION_REGISTRY = "application/http/registry";

    // Relational databases
    public static final String RELATIONAL_DATABASE = "database/relational";
    public static final String DB2_DATABASE = "database/db2";
    public static final String HSQL_DATABASE = "database/hsql";
    public static final String MYSQL_DATABASE = "database/mysql";
    public static final String POSTGRESQL_DATABASE = "database/postgresql";
    public static final String ORACLE_DATABASE = "database/oracle";
    public static final String MSSQL_DATABASE = "database/mssql";

    public static final String CASSANDRA = "database/cassandra";
    public static final String MONGODB = "database/mongodb";
    public static final String REDIS = "database/redis";
    public static final String NEO4J = "database/neo4j";
    public static final String ELASTIC_SEARCH = "index/elasticsearch";
    public static final String RABBITMQ = "message-broker/rabbitmq";
    public static final String LDAP = "directory/ldap";

    private ComponentType() {

    }

}

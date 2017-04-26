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
package io.cereebro.spring.boot.autoconfigure.jdbc;

import org.springframework.util.StringUtils;

import io.cereebro.core.ComponentType;

/**
 * Utility {@link Enum} for matching different database products. Same idea as
 * Spring Data JDBC extension DatabaseType
 * (http://docs.spring.io/spring-data/jdbc/docs/current/api/index.html?org/springframework/data/jdbc/support/DatabaseType.html)
 * 
 * @author lwarrot
 *
 */
public enum DbType {

    // @formatter:off
    RELATIONAL(ComponentType.RELATIONAL_DATABASE, "unmatchable_-_string"), 
    MYSQL(ComponentType.MYSQL_DATABASE,"mysql"), 
    MSSQL(ComponentType.MSSQL_DATABASE, "microsoft"), 
    ORACLE(ComponentType.ORACLE_DATABASE,"oracle"), 
    POSTGRESQL(ComponentType.POSTGRESQL_DATABASE, "postgre"), 
    HSQL(ComponentType.HSQL_DATABASE, "hsql"), 
    DB2(ComponentType.DB2_DATABASE, "db2");
    // @formatter:on

    private String componentType;
    private String productName;

    private DbType(String componentType, String productName) {
        this.componentType = componentType;
        this.productName = productName;
    }

    public String componentType() {
        return componentType;
    }

    public String productName() {
        return productName;
    }

    public static DbType findByProductName(String productName) {
        if (StringUtils.hasText(productName)) {
            String lowerCase = productName.toLowerCase();
            for (DbType db : values()) {
                if (lowerCase.contains(db.productName)) {
                    return db;
                }
            }
        }
        return RELATIONAL;
    }

}
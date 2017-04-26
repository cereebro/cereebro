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

import org.assertj.core.api.Assertions;
import org.junit.Test;

import io.cereebro.core.ComponentType;

/**
 * {@link DbType} unit tests.
 * 
 * @author michaeltecourt
 *
 */
public class DbTypeTest {

    @Test
    public void testAttributes() {
        Assertions.assertThat(DbType.MYSQL.productName()).isEqualTo("mysql");
        Assertions.assertThat(DbType.MYSQL.componentType()).isEqualTo(ComponentType.MYSQL_DATABASE);
    }

    @Test
    public void findByUnknownProductNameShouldReturnDefault() {
        Assertions.assertThat(DbType.findByProductName("kangaroo")).isEqualTo(DbType.RELATIONAL);
    }

    @Test
    public void findByExistingProductNameShouldReturnMatchingValue() {
        Assertions.assertThat(DbType.findByProductName("mysql")).isEqualTo(DbType.MYSQL);
    }

    @Test
    public void findByNullProductNameShouldReturnDefault() {
        Assertions.assertThat(DbType.findByProductName(null)).isEqualTo(DbType.RELATIONAL);
    }

    @Test
    public void findByEmptyProductNameShouldReturnDefault() {
        Assertions.assertThat(DbType.findByProductName("")).isEqualTo(DbType.RELATIONAL);
    }

}

package io.cereebro.autoconfigure;

import java.sql.Connection;

import javax.sql.DataSource;

import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.datastax.driver.core.Session;

@SpringBootApplication
public class DummyApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(DummyApplication.class, args);
    }

    @Bean
    @Profile("database")
    public DataSource dataSource() throws Exception {
        DataSource ds = Mockito.mock(DataSource.class);
        Connection c = Mockito.mock(Connection.class);
        Mockito.when(ds.getConnection()).thenReturn(c);
        Mockito.when(c.getCatalog()).thenReturn("catalog");
        return ds;
    }

    @Bean
    @Profile("cassandra")
    public Session session() {
        Session s = Mockito.mock(Session.class);
        Mockito.when(s.getLoggedKeyspace()).thenReturn("keyspace");
        return s;
    }

}

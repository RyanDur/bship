package com.bship;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.flywaydb.core.Flyway;

public class DBHelper {

    public static DataSource reset() {
        DataSource dataSource = new DataSource();
        dataSource.setUrl("jdbc:mysql://localhost/bs");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("password");
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.clean();
        flyway.migrate();

        return dataSource;
    }
}

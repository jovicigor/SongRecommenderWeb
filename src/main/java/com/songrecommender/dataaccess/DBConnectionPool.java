package com.songrecommender.dataaccess;


import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

public final class DBConnectionPool {

    private static final BasicDataSource dataSource = new BasicDataSource();

    static {
        dataSource.setUrl("jdbc:mysql://localhost:3306/pesme?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UCT");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
    }

    private DBConnectionPool() {
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        return connection;
    }
}
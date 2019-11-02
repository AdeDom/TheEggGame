package com.adedom.library;

import java.sql.Connection;

public class Execute {

    private static Connection connection;

    public Execute() {
    }

    public Execute(Connection connection) {
        Execute.connection = connection;
    }

    public ExecuteCommit execute(String sql) {
        return new ExecuteCommit(connection, sql);
    }
}

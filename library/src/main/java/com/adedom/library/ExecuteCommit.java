package com.adedom.library;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExecuteCommit {

    private static Connection connection;

    private static String sql;

    public ExecuteCommit() {
    }

    public ExecuteCommit(Connection connection, String sql) {
        ExecuteCommit.connection = connection;
        ExecuteCommit.sql = sql;
    }

    public void commit() {
        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                assert statement != null;
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void commit(ExecuteUpdate update) {
        Statement statement = null;

        try {
            statement = connection.createStatement();
            int result = statement.executeUpdate(sql);
            if (result == 1) {
                update.onComplete();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                assert statement != null;
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void commit(ExecuteQuery query) {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            query.onComplete(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                assert resultSet != null;
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

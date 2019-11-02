package com.adedom.library;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CallCommit {

    private static Connection connection;

    private static String storedProcedureName;

    private static ArrayList<String> values;

    public CallCommit() {
    }

    public CallCommit(Connection connection, String storedProcedureName) {
        CallCommit.connection = connection;
        CallCommit.storedProcedureName = storedProcedureName;
        values = new ArrayList<>();
    }

    public void commit() {
        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(spFormat(values));
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
            int result = statement.executeUpdate(spFormat(values));
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
            resultSet = statement.executeQuery(spFormat(values));
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

    public CallCommit parameter(String values) {
        CallCommit.values.add("'" + values + "'");
        return new CallCommit();
    }

    private String spFormat(ArrayList<String> values) {
        String sql = "CALL " + storedProcedureName + "(";
        for (String s : values) sql += s + ",";
        if (!values.isEmpty()) sql = sql.substring(0, sql.length() - 1);
        sql += ")";
        return sql;
    }
}

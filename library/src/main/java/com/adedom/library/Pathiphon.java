package com.adedom.library;

import android.content.Context;
import android.os.StrictMode;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Pathiphon {

    public Pathiphon() {
    }

    private static final String IP = "192.168.43.22";
    private static final String DB_NAME = "the_egg_game";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "abc456";
    private static final String BASE_URL = "http://" + IP + "/the-egg-game/";

    public static Connection connection() {
        return connection(IP, DB_NAME, USERNAME, PASSWORD);
    }

    @Deprecated
    public static Connection connection(String ip, String dbName, String username, String password) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + ip + "/" + dbName + "?useUnicode=true&characterEncoding=utf-8";
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Deprecated
    public static Execute connection(Connection connection) {
        return new Execute(connection);
    }

    @Deprecated
    public static Call with(Connection connection) {
        return new Call(connection);
    }

    public static ExecuteCommit execute(String sql) {
        return new ExecuteCommit(connection(), sql);
    }

    public static CallCommit call(String storedProcedureName) {
        return new CallCommit(connection(), storedProcedureName);
    }

    public static void completed(Context context) {
        Toast.makeText(context, R.string.completed, Toast.LENGTH_SHORT).show();
    }

    public static void failed(Context context) {
        Toast.makeText(context, R.string.failed, Toast.LENGTH_LONG).show();
    }

    public static Boolean isEmpty(EditText editText, String error) {
        if (editText.getText().toString().trim().isEmpty()) {
            editText.requestFocus();
            editText.setError(error);
            return true;
        }
        return false;
    }
}

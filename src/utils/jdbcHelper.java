/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author luuvi
 */
public class jdbcHelper {
    public static PreparedStatement getStmt(String sql, Object... args) throws SQLException {
        
        Connection connection = DB_Connect.getConnection();
        PreparedStatement preparedStatement = null;

        if (sql.trim().startsWith("{")) {
            preparedStatement = connection.prepareCall(sql);
        } else {
            preparedStatement = connection.prepareStatement(sql);
        }

        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject(i + 1, args[i]);
        }

        return preparedStatement;
    }

    public static int update(String sql, Object... args) {
        try {
            PreparedStatement preparedStatement = getStmt(sql, args);
            try {
                return preparedStatement.executeUpdate();
            } finally {
                preparedStatement.getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute SQL update: " + e.getMessage());
        }
    }

    public static ResultSet query(String sql, Object... args) {
        try {
            PreparedStatement pePreparedStatement = getStmt(sql, args);
            return pePreparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute SQL query: " + e.getMessage());
        }
    }

    public static Object value(String sql, Object... args) {
        try {
            ResultSet resultSet = query(sql, args);
            if (resultSet.next()) {
                return resultSet.getObject(0);
            }
            resultSet.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

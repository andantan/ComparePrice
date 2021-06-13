package com.dev.DAO;

import java.sql.*;

public abstract class abstDAO {
    protected Connection connect() {
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/compareprice?characterEncoding=UTF-8", "root", "1234");
        } catch (ClassNotFoundException | SQLException throwables) {
            System.out.println("[com.dev.DAO.abstDAO::connect] " + throwables.getMessage());
        }

        return connection;
    }

    protected void close(PreparedStatement preparedStatement, Connection connection) throws SQLException {
        if (preparedStatement != null) preparedStatement.close();
        if (connection != null) connection.close();
    }

    protected void close(PreparedStatement preparedStatement, Connection connection, ResultSet resultSet) throws SQLException {
        if (resultSet != null) resultSet.close();
        close(preparedStatement, connection);
    }
}

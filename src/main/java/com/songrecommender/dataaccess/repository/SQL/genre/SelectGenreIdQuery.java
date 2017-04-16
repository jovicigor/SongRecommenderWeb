package com.songrecommender.dataaccess.repository.SQL.genre;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class SelectGenreIdQuery {

    private PreparedStatement preparedStatement;
    private static final String SQL = "SELECT id FROM genre WHERE name= ?";

    public SelectGenreIdQuery(Connection connection) throws SQLException {
        preparedStatement = connection.prepareStatement(SQL);
    }

    public SelectGenreIdQuery byName(String genreName) throws SQLException {
        preparedStatement.setString(1, genreName.trim());
        return this;
    }

    public Optional<Integer> execute() throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return Optional.of(resultSet.getInt("id"));
        }
        return Optional.empty();
    }
}

package com.songrecommender.dataaccess.repository.SQL.song;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class SelectSongIdQuery {

    private PreparedStatement preparedStatement;
    private static final String SQL = "SELECT id FROM track WHERE remote_id = ?";

    public SelectSongIdQuery(Connection connection) throws SQLException {
        preparedStatement = connection.prepareStatement(SQL);
    }

    public SelectSongIdQuery byRemoteId(String remoteId) throws SQLException {
        preparedStatement.setString(1, remoteId);
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

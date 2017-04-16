package com.songrecommender.dataaccess.repository.SQL.song;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class SelectSongClusterQuery {

    private PreparedStatement preparedStatement;
    private static final String SQL = "SELECT cluster FROM track WHERE remote_id = ?";

    public SelectSongClusterQuery(Connection connection) throws SQLException {
        preparedStatement = connection.prepareStatement(SQL);
    }

    public SelectSongClusterQuery byRemoteId(String remoteId) throws SQLException {
        preparedStatement.setString(1, remoteId);
        return this;
    }

    public Optional<Integer> execute() throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return Optional.of(resultSet.getInt("cluster"));
        }
        return Optional.empty();
    }
}
